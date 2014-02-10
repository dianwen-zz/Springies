package utilities;
import java.io.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class FileChooser extends JPanel implements ActionListener {
    public File getFile() {
    	JFileChooser fc = new JFileChooser();
    	fc.setCurrentDirectory(new File("assets"));
            int returnVal = fc.showOpenDialog(FileChooser.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                return file;
            } else {
                getFile();
            }
            return null;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
