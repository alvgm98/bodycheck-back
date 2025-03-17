package bodycheck_back.bodycheck.pdf;

import java.awt.Color;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Page event handler to add page numbers to the PDF
 */
public class PageNumberFooter extends PdfPageEventHelper {
   private final Font footerFont;
   private final Color primaryColor;

   public PageNumberFooter(Color primaryColor) {
      this.primaryColor = primaryColor;
      this.footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8, primaryColor);
   }

   @Override
   public void onEndPage(PdfWriter writer, Document document) {
      PdfContentByte cb = writer.getDirectContent();

      // Add page number at the bottom
      String text = "Página " + writer.getPageNumber();
      float textBase = document.bottom() - 20;

      // Add page number
      ColumnText.showTextAligned(
            cb, Element.ALIGN_CENTER,
            new Phrase(text, footerFont),
            (document.right() + document.left()) / 2,
            textBase, 0);

      // Add a thin line above the page number
      cb.setColorStroke(primaryColor);
      cb.setLineWidth(0.5f);
      cb.moveTo(document.left(), textBase + 10);
      cb.lineTo(document.right(), textBase + 10);
      cb.stroke();
   }
}