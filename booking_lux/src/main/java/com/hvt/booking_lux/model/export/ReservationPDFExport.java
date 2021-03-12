//package com.hvt.booking_lux.model.export;
//
//import com.hvt.booking_lux.model.Reservation;
//
//public class ReservationPDFExport {
//
//    private Reservation reservation;
//
//    public ReservationPDFExport(Reservation reservation) {
//        this.reservation = reservation;
//    }
//
//    private void writeTableHeader(PdfPTable table) {
//        PdfPCell cell = new PdfPCell();
//        cell.setBackgroundColor(Color.BLUE);
//        cell.setPadding(5);
//
//        Font font = FontFactory.getFont(FontFactory.HELVETICA);
//        font.setColor(Color.WHITE);
//
//        cell.setPhrase(new Phrase("User ID", font));
//
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("E-mail", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Full Name", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Roles", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Enabled", font));
//        table.addCell(cell);
//    }
//
//    private void writeTableData(PdfPTable table) {
//        for (User user : listUsers) {
//            table.addCell(String.valueOf(user.getId()));
//            table.addCell(user.getEmail());
//            table.addCell(user.getFullName());
//            table.addCell(user.getRoles().toString());
//            table.addCell(String.valueOf(user.isEnabled()));
//        }
//    }
//
//    public void export(HttpServletResponse response) throws DocumentException, IOException {
//        Document document = new Document(PageSize.A4);
//        PdfWriter.getInstance(document, response.getOutputStream());
//
//        document.open();
//        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        font.setSize(18);
//        font.setColor(Color.BLUE);
//
//        Paragraph p = new Paragraph("List of Users", font);
//        p.setAlignment(Paragraph.ALIGN_CENTER);
//
//        document.add(p);
//
//        PdfPTable table = new PdfPTable(5);
//        table.setWidthPercentage(100f);
//        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
//        table.setSpacingBefore(10);
//
//        writeTableHeader(table);
//        writeTableData(table);
//
//        document.add(table);
//
//        document.close();
//
//    }
//
//}
