package com.sweetshop.util;

import com.sweetshop.model.Sale;
import com.sweetshop.model.SaleItem;
import com.sweetshop.service.InventoryService;
import com.sweetshop.model.SweetItem;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PDFGenerator {

    public static void generateInvoice(Sale sale, List<SaleItem> items) {
        try {
            String dir = "invoices";
            java.io.File folder = new java.io.File(dir);
            if (!folder.exists()) folder.mkdirs();

            String filename = dir + "/invoice_" + sale.getSaleId() + ".pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // ===== HEADER =====
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("🍬 Sweet Shop Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Date: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
            document.add(new Paragraph("Sale ID: " + sale.getSaleId()));
            document.add(new Paragraph("User ID: " + sale.getUserId()));
            document.add(new Paragraph("------------------------------------------------------------"));

            // ===== TABLE =====
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell("Item ID");
            table.addCell("Name");
            table.addCell("Qty");
            table.addCell("Price (₹)");
            table.addCell("Subtotal (₹)");

            InventoryService inventoryService = new InventoryService();
            for (SaleItem item : items) {
                SweetItem si = inventoryService.getAllItems()
                        .stream()
                        .filter(i -> i.getItemId() == item.getItemId())
                        .findFirst()
                        .orElse(null);

                table.addCell(String.valueOf(item.getItemId()));
                table.addCell(si != null ? si.getName() : "Unknown");
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.format("%.2f", item.getSubtotal() / item.getQuantity()));
                table.addCell(String.format("%.2f", item.getSubtotal()));
            }

            document.add(table);
            document.add(new Paragraph("------------------------------------------------------------"));

            Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph total = new Paragraph("Total Amount: ₹" + String.format("%.2f", sale.getTotalAmount()), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.add(new Paragraph("\nThank you for shopping with us! Come again soon 💖", new Font(Font.FontFamily.HELVETICA, 12)));

            document.close();
            System.out.println("✅ Invoice generated: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
