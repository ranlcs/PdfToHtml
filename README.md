# PdfToHtml
Transform a pdf file into a html file



//from https://stackoverflow.com/questions/30641909/java-how-to-extract-text-by-a-selected-area-from-a-pdf-file-using-itext
//extract data by taping the area coordinations
import java.io.IOException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

/**
 * Créer par Malek Boubakri le 03/06/2015 à 15:45.
 */

public class ExtractPageContentArea {
 //
    public void parsePdf(float x,float y,float width,float height,String pdf) throws IOException {
        PdfReader reader = new PdfReader(pdf);
        Rectangle rect = new Rectangle(x, y, width, height);
        RenderFilter filter = new RegionTextRenderFilter(rect);
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            System.out.println(PdfTextExtractor.getTextFromPage(reader, i, strategy));
        }
        reader.close();
    }
}


//draw rectangle and save needed coordinations
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MouseTracker extends JFrame implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    private final JLabel mousePosition;
    int x1, x2, y1, y2;
    int w, h;
    private final JLabel recStart;
    private final JLabel recStop;
    private final JLabel cords; // set up GUI and register mouse event handlers
    private final ArrayList< Rectangle > rectangles = new ArrayList< Rectangle >();
    private boolean isNewRect = true;

    public MouseTracker() {
        super( "Rectangle Drawer" );

        this.mousePosition = new JLabel();
        this.mousePosition.setHorizontalAlignment( SwingConstants.CENTER );
        getContentPane().add( this.mousePosition, BorderLayout.CENTER );

        JLabel text1 = new JLabel();
        text1.setText( "At the center the mouse pointer's coordinates will be displayed." );
        getContentPane().add( text1, BorderLayout.SOUTH );

        this.recStart = new JLabel();
        getContentPane().add( this.recStart, BorderLayout.WEST );

        this.recStop = new JLabel();
        getContentPane().add( this.recStop, BorderLayout.EAST );

        this.cords = new JLabel();
        getContentPane().add( this.cords, BorderLayout.NORTH );

        addMouseListener( this ); // listens for own mouse and
        addMouseMotionListener( this ); // mouse-motion events

        setSize( 800, 600 );
        setVisible( true );

    }

// MouseListener event handlers // handle event when mouse released immediately after press 
    public void mouseClicked( final MouseEvent event ) {
        this.mousePosition.setText( "Clicked at [" + event.getX() + ", " + event.getY() + "]" );

        repaint();
    }

// handle event when mouse pressed 
    public void mousePressed( final MouseEvent event ) {

        this.mousePosition.setText( "Pressed at [" + ( this.x1 = event.getX() ) + ", " + ( this.y1 = event.getY() ) + "]" );

        this.recStart.setText( "Start:  [" + this.x1 + ", " + this.y1 + "]" );

        repaint();
    }

// handle event when mouse released after dragging 
    public void mouseReleased( final MouseEvent event ) {
        this.mousePosition.setText( "Released at [" + ( this.x2 = event.getX() ) + ", " + ( this.y2 = event.getY() ) + "]" );

        this.recStop.setText( "End:  [" + this.x2 + ", " + this.y2 + "]" );

        Rectangle rectangle = getRectangleFromPoints();

        this.rectangles.add( rectangle );

        this.w = this.h = this.x1 = this.y1 = this.x2 = this.y2 = 0;
        this.isNewRect = true;

        repaint();
    }

    private Rectangle getRectangleFromPoints() {
        int width = this.x1 - this.x2;
        int height = this.y1 - this.y2;
        Rectangle rectangle = new Rectangle( width < 0 ? this.x1
            : this.x2, height < 0 ? this.y1
            : this.y2, Math.abs( width ), Math.abs( height ) );

        return rectangle;
    }

// handle event when mouse enters area 
    public void mouseEntered( final MouseEvent event ) {
        this.mousePosition.setText( "Mouse entered at [" + event.getX() + ", " + event.getY() + "]" );
        repaint();
    }

// handle event when mouse exits area 
    public void mouseExited( final MouseEvent event ) {
        this.mousePosition.setText( "Mouse outside window" );
        repaint();
    }

// MouseMotionListener event handlers // handle event when user drags mouse with button pressed 
    public void mouseDragged( final MouseEvent event ) {
        this.mousePosition.setText( "Dragged at [" + ( this.x2 = event.getX() ) + ", " + ( this.y2 = event.getY() ) + "]" ); // call repaint which calls paint repaint();

        this.isNewRect = false;

        repaint();
    }

// handle event when user moves mouse 
    public void mouseMoved( final MouseEvent event ) {
        this.mousePosition.setText( "Moved at [" + event.getX() + ", " + event.getY() + "]" );
        repaint();
    }

    @Override
    public void paint( final Graphics g ) {
        super.paint( g ); // clear the frame surface 
        g.drawString( "Start Rec Here", this.x1, this.y1 );
        g.drawString( "End Rec Here", this.x2, this.y2 );

        Rectangle newRectangle = getRectangleFromPoints();
        if ( !this.isNewRect ) {
            g.drawRect( newRectangle.x, newRectangle.y, newRectangle.width, newRectangle.height );
        }

        for( Rectangle rectangle : this.rectangles ) {
            g.drawRect( rectangle.x, rectangle.y, rectangle.width, rectangle.height );
        }

        this.cords.setText( "w = " + this.w + ", h = " + this.h );

    }

    public static void main( final String args[] ) {
        MouseTracker application = new MouseTracker();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
}

//create rectangle and defines the bounds of the text
PdfReader reader = new PdfReader(pdf);
PrintWriter out= new PrintWriter(new FileOutputStream(txt));
//Creating the rectangle
Rectangle rect=new Rectangle(70,80,420,500);
//creating a filter based on the rectangle
RenderFilter filter = new RegionTextRenderFilter(rect);
TextExtractionStrategy strategy;
for(int i=1;i<=reader.getNumberOfPages();i+){
    //setting the filter on the text extraction strategy
    strategy= new FilteredTextRenderListener(
      new LocationTextExtractionStrategy(),filter);
    out.println(PdfTextExtractor.getTextFromPage(reader,i,strategy));
}
out.flush();out.close();


//pdf to textFile
public class PdfConverter {

/** The original PDF that will be parsed. */
public static final String pdfFileName = "jdbc_tutorial.pdf";
/** The resulting text file. */
public static final String RESULT = "preface.txt";

/**
 * Parses a PDF to a plain text file.
 * @param pdf the original PDF
 * @param txt the resulting text
 * @throws IOException
 */
public void parsePdf(String pdf, String txt) throws IOException {
    PdfReader reader = new PdfReader(pdf);
    PdfReaderContentParser parser = new PdfReaderContentParser(reader);
    PrintWriter out = new PrintWriter(new FileOutputStream(txt));

    TextExtractionStrategy strategy;
    for (int i = 1; i <= reader.getNumberOfPages(); i++) {
        strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
        out.println(strategy.getResultantText());
        System.out.println(strategy.getResultantText());
    }
    out.flush();
    out.close();
    reader.close();
}

/**
 * Main method.
 * @param    args    no arguments needed
 * @throws IOException
 */
public static void main(String[] args) throws IOException {
    new PdfConverter().parsePdf(pdfFileName, RESULT);
}

//location based extraction, finding text margins, extracting images
//http://what-when-how.com/itext-5/parsing-pdfs-part-2-itext-5/



finalPage.replace("\n",</br> ??

//https://howtodoinjava.com/java/library/read-generate-pdf-java-itext/
