package bodycheck_back.bodycheck.controllers;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

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
import com.lowagie.text.Image;
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

   @PostMapping("/generate")
   public ResponseEntity<byte[]> generatePdf(@RequestBody ExportCustomerPdfRequest exportCustomerPdfRequest) {
      CustomerDTO customerDTO = customerValidator.validateCustomerOwnership(exportCustomerPdfRequest.getCustomerId());

      try {
         // 1️⃣ Decodificamos las imagenes de Base64 a byte[]
         byte[] DurninWomersleyImageBytes = Base64.getDecoder()
               .decode(exportCustomerPdfRequest.getDurninWomersleyBase64Image());

         // 2️⃣ Creamos el documento PDF
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         Document document = new Document();
         PdfWriter.getInstance(document, baos);
         document.open();

         // 3️⃣ Convertir la imagen y agregarla al PDF
         Image image = Image.getInstance(DurninWomersleyImageBytes);
         image.scaleToFit(500, 300);
         document.add(image);

         document.close();

         // 4️⃣ Configurar la respuesta con el PDF generado
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_PDF);
         headers.setContentDisposition(ContentDisposition.attachment().filename("grafico.pdf").build());

         return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }

   @Data
   @Builder
   @NoArgsConstructor
   @AllArgsConstructor
   private static class ExportCustomerPdfRequest {
      private Long customerId;
      private String durninWomersleyBase64Image;
   }
}
