/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author PersonalCastro
 */
public class ListWithImage extends JLabel implements ListCellRenderer{


    
    private ImageIcon photo;

    
    public ListWithImage(String photo){
            
        ImageIcon imageIcon = new ImageIcon("img/Logo_Messenger_Mal.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        this.setPhoto(new ImageIcon(newimg));
        
    }

    public void setPhoto(ImageIcon photo) {
        this.photo = photo;
    }

    public ImageIcon getPhoto() {
        return photo;
    }
            
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        String valor = value.toString();
        this.setText(valor);
        if(!valor.equals("")){
            setIcon(this.getPhoto());
        }
        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        
        return this;
    }
    
}
