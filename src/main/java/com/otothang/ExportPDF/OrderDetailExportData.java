package com.otothang.ExportPDF;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.otothang.Service.OrderDetailSevice;
import com.otothang.models.Order;
import com.otothang.models.OrderDetail;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class OrderDetailExportData {
    private Order order;
    private OrderDetailSevice orderDetailSevice;

    public OrderDetailExportData(Order order, OrderDetailSevice orderDetailSevice) {
        this.order = order;
        this.orderDetailSevice = orderDetailSevice;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        List<OrderDetail> orderDetails = orderDetailSevice.getByOrderId(order);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("OrderID : " + order.getId() + "            " + "Customer Name : " + order.getUser().getFullName(), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, orderDetails);

        document.add(table);

        document.close();
    }

    private void writeTableHeader(PdfPTable table) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        // Thêm cột số thứ tự
        PdfPCell cell = new PdfPCell(new Phrase("STT", font));
        cell.setBackgroundColor(Color.BLUE);
        table.addCell(cell);

        // Thêm cột tên sản phẩm
        cell = new PdfPCell(new Phrase("Product name", font));
        cell.setBackgroundColor(Color.BLUE);
        table.addCell(cell);

        // Thêm cột giá
        cell = new PdfPCell(new Phrase("Price", font));
        cell.setBackgroundColor(Color.BLUE);
        table.addCell(cell);

        // Thêm cột số lượng
        cell = new PdfPCell(new Phrase("Quantity", font));
        cell.setBackgroundColor(Color.BLUE);
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table, List<OrderDetail> orderDetails) {
        int index = 1;
        for (OrderDetail orderDetail : orderDetails) {
            table.addCell(String.valueOf(index));
            table.addCell(orderDetail.getProduct().getProductName());
            table.addCell(orderDetail.getPrice().toString());
            table.addCell(orderDetail.getQuantity().toString());
            index++;
        }
    }
}
