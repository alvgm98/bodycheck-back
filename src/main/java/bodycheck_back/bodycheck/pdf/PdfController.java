package bodycheck_back.bodycheck.pdf;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Locale;
import java.awt.Color;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import bodycheck_back.bodycheck.controllers.util.CustomerValidator;
import bodycheck_back.bodycheck.models.dtos.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

   private final CustomerValidator customerValidator;

   // Define primary color and derived colors
   private static final Color PRIMARY_COLOR = new Color(16, 157, 138); // #109d8a
   private static final Color PRIMARY_DARK = new Color(13, 126, 111); // Darker shade
   private static final Color PRIMARY_LIGHT = new Color(232, 245, 243); // Lighter shade for backgrounds
   private static final Color TEXT_COLOR = new Color(51, 51, 51); // Dark gray for text

   // Define fonts with updated colors
   private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, PRIMARY_COLOR);
   private static final Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, PRIMARY_DARK);
   private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
   private static final Font NORMAL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, TEXT_COLOR);
   private static final Font BOLD_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, TEXT_COLOR);

   private static final Color HEADER_BG_COLOR = PRIMARY_COLOR;
   private static final Color ALTERNATE_ROW_COLOR = PRIMARY_LIGHT;

   @PostMapping("/generate")
   public ResponseEntity<byte[]> generatePdf(@RequestBody ExportCustomerPdfRequest exportCustomerPdfRequest) {
      CustomerDTO customerDTO = customerValidator.validateCustomerOwnership(exportCustomerPdfRequest.getCustomerId());

      try {
         // 1️⃣ Decodificamos las imagenes de base64 a bytes
         byte[] durninWomersleyImageBytes = Base64.getDecoder()
               .decode(exportCustomerPdfRequest.getDurninWomersleyBase64Image());

         byte[] jacksonPollock7ImageBytes = Base64.getDecoder()
               .decode(exportCustomerPdfRequest.getJacksonPollock7Base64Image());

         byte[] jacksonPollock3ImageBytes = Base64.getDecoder()
               .decode(exportCustomerPdfRequest.getJacksonPollock3Base64Image());

         byte[] weltmanImageBytes = Base64.getDecoder()
               .decode(exportCustomerPdfRequest.getWeltmanBase64Image());

         byte[] navyTapeImageBytes = Base64.getDecoder()
               .decode(exportCustomerPdfRequest.getNavyTapeBase64Image());

         // 2️⃣ Creamos el PDF de tamaño A4 con margenes
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         Document document = new Document(PageSize.A4, 36, 36, 54, 36); // margenes: left, right, top, bottom
         PdfWriter writer = PdfWriter.getInstance(document, baos);

         // Añadimos a las páginas el número de página
         writer.setPageEvent(new PageNumberFooter(PRIMARY_COLOR));

         document.open();

         // 3️⃣ Add logo and title
         addReportHeader(document, customerDTO);

         // 4️⃣ Add customer information section
         addCustomerInformation(document, customerDTO);

         // 6️⃣ Add images with captions and descriptions
         addImageWithCaption(document, durninWomersleyImageBytes, "Formula Durnin-Womersley + Siri");

         document.newPage();

         addImageWithCaption(document, jacksonPollock7ImageBytes, "Formula Jackson-Pollock 7 Pliegues + Siri");

         addImageWithCaption(document, jacksonPollock3ImageBytes, "Formula Jackson-Pollock 3 Pliegues + Siri");

         document.newPage();

         addImageWithCaption(document, weltmanImageBytes, "Formula Weltman");

         addImageWithCaption(document, navyTapeImageBytes, "Formula Navy Tape");

         document.close();

         // 7️⃣ Configure response with generated PDF
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_PDF);
         String filename = customerDTO.getLastName() + "_" + customerDTO.getFirstName() + "_BodyComposition.pdf";
         headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

         return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }

   /**
    * Añade la cabecera del informe con el logo y el título
    */
   private void addReportHeader(Document document, CustomerDTO customer) throws Exception {
      // 1️⃣ Añadimos el logo
      ClassPathResource resource = new ClassPathResource("static/logo.png");
      Image logoImage = Image.getInstance(resource.getInputStream().readAllBytes());
      logoImage.scaleToFit(60, 60);
      logoImage.setAlignment(Element.ALIGN_CENTER);
      document.add(logoImage);

      // 2️⃣ Añadimo el titulo
      Paragraph title = new Paragraph("INFORME DE COMPOSICIÓN CORPORAL", TITLE_FONT);
      title.setAlignment(Element.ALIGN_CENTER);
      title.setSpacingBefore(10);
      document.add(title);

      // Generamos la fecha de hoy y convertimos la primera letra del mes a mayúscula
      String date = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es-ES")));
      String[] dateSplit = date.split(" ");
      dateSplit[2] = dateSplit[2].substring(0, 1).toUpperCase() + dateSplit[2].substring(1);
      date = String.join(" ", dateSplit);

      // 3️⃣ Añadimos la fecha
      Paragraph dateParagraph = new Paragraph("Fecha: " + date, NORMAL_FONT);
      dateParagraph.setAlignment(Element.ALIGN_CENTER);
      dateParagraph.setSpacingAfter(20);
      document.add(dateParagraph);
   }

   /**
    * Añadimos la información del cliente al informe
    */
   private void addCustomerInformation(Document document, CustomerDTO customer) throws DocumentException {
      // Titulo de la tabla
      Paragraph customerTitle = new Paragraph("INFORMACIÓN DEL CLIENTE", SUBTITLE_FONT);
      customerTitle.setSpacingBefore(10);
      customerTitle.setSpacingAfter(10);
      document.add(customerTitle);

      // Generamos una tabla con dos columnas
      PdfPTable table = new PdfPTable(2);
      table.setWidthPercentage(100);
      table.setSpacingAfter(20);

      // Añadimos el Header de la tabla
      PdfPCell headerCell1 = new PdfPCell(new Phrase("Campo", HEADER_FONT));
      headerCell1.setBackgroundColor(HEADER_BG_COLOR);
      headerCell1.setPadding(5);

      PdfPCell headerCell2 = new PdfPCell(new Phrase("Información", HEADER_FONT));
      headerCell2.setBackgroundColor(HEADER_BG_COLOR);
      headerCell2.setPadding(5);

      table.addCell(headerCell1);
      table.addCell(headerCell2);

      // Calculamos la edad del cliente
      int age = 0;
      if (customer.getBirthdate() != null) {
         age = Period.between(customer.getBirthdate(), LocalDate.now()).getYears();
      }

      // Añadimos la información a la talba
      addCustomerInfoRow(table, "Nombre", customer.getFirstName() + " " + customer.getLastName());
      addCustomerInfoRow(table, "Email", customer.getEmail());
      addCustomerInfoRow(table, "Teléfono", customer.getPhone());
      addCustomerInfoRow(table, "Fecha de nacimiento",
            customer.getBirthdate() != null
                  ? customer.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " (" + age + " años)"
                  : "");
      addCustomerInfoRow(table, "Altura", customer.getHeight() != null ? customer.getHeight() + " cm" : "");
      addCustomerInfoRow(table, "Género", customer.getGender());
      addCustomerInfoRow(table, "Etnia", customer.getEthnicity());
      addCustomerInfoRow(table, "Objetivo", customer.getTarget());

      document.add(table);

      // Add observations if present
      if (customer.getObservations() != null && !customer.getObservations().trim().isEmpty()) {
         PdfPTable obsTable = new PdfPTable(1);
         obsTable.setWidthPercentage(100);

         PdfPCell obsHeaderCell = new PdfPCell(new Phrase("Observaciones", HEADER_FONT));
         obsHeaderCell.setBackgroundColor(HEADER_BG_COLOR);
         obsHeaderCell.setPadding(5);
         obsTable.addCell(obsHeaderCell);

         PdfPCell obsContentCell = new PdfPCell(new Phrase(customer.getObservations(), NORMAL_FONT));
         obsContentCell.setPadding(5);
         obsTable.addCell(obsContentCell);

         document.add(obsTable);
      }
   }

   /**
    * Añade una fila a la tabla de información del cliente
    */
   private void addCustomerInfoRow(PdfPTable table, String label, String value) {
      PdfPCell labelCell = new PdfPCell(new Phrase(label, BOLD_FONT));
      labelCell.setBorder(Rectangle.BOX);
      labelCell.setBorderColor(PRIMARY_COLOR);
      labelCell.setBackgroundColor(ALTERNATE_ROW_COLOR);
      labelCell.setPadding(5);

      PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "", NORMAL_FONT));
      valueCell.setBorder(Rectangle.BOX);
      valueCell.setBorderColor(PRIMARY_COLOR);
      valueCell.setBackgroundColor(ALTERNATE_ROW_COLOR);
      valueCell.setPadding(5);

      table.addCell(labelCell);
      table.addCell(valueCell);
   }

   /**
    * Añadimos una imagenes con título
    */
   private void addImageWithCaption(Document document, byte[] imageBytes, String title)
         throws Exception {
      // Create a table for the section
      PdfPTable sectionTable = new PdfPTable(1);
      sectionTable.setWidthPercentage(100);
      sectionTable.setSpacingBefore(15);

      // Add title cell
      PdfPCell titleCell = new PdfPCell(new Phrase(title, BOLD_FONT));
      titleCell.setBackgroundColor(PRIMARY_LIGHT);
      titleCell.setBorderColor(PRIMARY_COLOR);
      titleCell.setBorderWidth(1);
      titleCell.setPadding(5);
      sectionTable.addCell(titleCell);

      // Añadimos la imagen
      Image image = Image.getInstance(imageBytes);
      image.scaleToFit(500, 300);

      PdfPCell imageCell = new PdfPCell();
      imageCell.addElement(image);
      imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      imageCell.setPadding(10);
      imageCell.setBorderColor(PRIMARY_COLOR);
      imageCell.setBorderWidth(0.5f);
      sectionTable.addCell(imageCell);

      document.add(sectionTable);
   }

   @Data
   @Builder
   @NoArgsConstructor
   @AllArgsConstructor
   private static class ExportCustomerPdfRequest {
      private Long customerId;
      private String durninWomersleyBase64Image;
      private String jacksonPollock7Base64Image;
      private String jacksonPollock3Base64Image;
      private String weltmanBase64Image;
      private String navyTapeBase64Image;
   }
}