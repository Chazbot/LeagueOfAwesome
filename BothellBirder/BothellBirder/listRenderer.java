/**
 * 
 */
package BothellBirder;

import java.awt.Component;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;



/**
 * @author Bret Van Hof
 *
 */
public class listRenderer extends DefaultListCellRenderer 
{

	private ImageIcon image;
	private String name;
	private Map<Object, ImageIcon> icons = null;

	public listRenderer(Map<Object, ImageIcon> icons)
	{
		setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        this.icons = icons;
	}
	public Component getListCellRendererComponent(
		    JList list, Object value, int index,
		    boolean isSelected, boolean cellHasFocus) 
	{
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //Set the icon and text.  If icon was null, say so.
        image = icons.get(value);
        String name = value.toString();
        setIcon(image);
        if (image != null) {
            setText(name);
            setFont(list.getFont());
        } else {
            setText(name + " (no image available)");
        }

        return this;
	}
}
