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
	private Map<Object, ImageIcon> icons = null;
	private Map<Integer, String> valueToBirdName;

	public listRenderer(Map<Object, ImageIcon> icons, Map<Integer, String> valueToBirdNameMap)
	{
		setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        this.icons = icons;
        valueToBirdName = valueToBirdNameMap;
	}
	public Component getListCellRendererComponent(
		    JList<?> list, Object value, int index,
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
        image = icons.get(index);
        String name = valueToBirdName.get(index);
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
