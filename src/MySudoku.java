import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// This class generates the  display window.
public class MySudoku {
	public static byte[][] sudoku = new byte[729][82];  //global array for sudoku solution
	public static byte step = 0; //global variable for solution step

	private static final int WindowWidth = 777; //its 777 pixels wide
	private static final int WindowHeight = 636; //its 636 pixels high
	
    public static void ShowGUI() {
    	    Smethods.start(sudoku); //start array at step 0 has no numbers selected
    	   
		    final byte border = 14;  //border for display
		    JFrame f = new JFrame("Sudoku v3.0");
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        BufferedImage image = null;
	            try {
					image = ImageIO.read(new File("sudoku.png"));
				} catch (IOException e) {
				}//end of try/catch
	        f.setResizable(false);  //not to be resized
	        f.setIconImage(image);
		    f.setSize(WindowWidth, WindowHeight);  //size fixed by size of display and borders
		    f.setLocation(0, 0); //start top left
		    f.setLayout(new BorderLayout());  //north south east west and centre		   
		    
		    f.add(new SPanel(new Dimension(WindowWidth,border)),  BorderLayout.NORTH);
		    f.add(new SPanel(new Dimension(WindowWidth,border)),  BorderLayout.SOUTH);
		    f.add(new SPanel(new Dimension(border,WindowHeight)),   BorderLayout.EAST);
		    f.add(new SPanel(new Dimension(0,WindowHeight)),   BorderLayout.WEST);  //set the borders

	        DisplayPanel dp =new  DisplayPanel();
	        dp.setBackground(Color.WHITE);  //set the background of the sudoku display black
	        f.add(dp, BorderLayout.CENTER);  //add the sudoku display panel
	      
	        f.setVisible(true);	
	 }
		    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	ShowGUI(); 
            }			
        });
    }
    
}