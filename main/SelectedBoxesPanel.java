package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.jme3.scene.Geometry;

public class SelectedBoxesPanel extends JPanel implements ActionListener
{
	private final Timer timer;
	public ListSelectionModel listSelectionModel;
	public JList<Part> list;
	public static JFrame thisFrame;
	private static final long serialVersionUID = 1L;

	public static void initialize()
	{
		thisFrame = new JFrame("Boxes");
		thisFrame.add(new SelectedBoxesPanel());
		thisFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		thisFrame.setSize(600, 400);
		thisFrame.setLocationRelativeTo(null);
		thisFrame.setResizable(false);
		thisFrame.setVisible(false);
		thisFrame.pack();
	}

	public static void startSelectedBoxesPanel()
	{
		thisFrame.setVisible(true);
	}

	public SelectedBoxesPanel()
	{
		super(new BorderLayout());

		timer = new Timer(400, this);
		timer.start();

		list = new JList<Part>(Part.getAllParts().toArray(new Part[0]));
		listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
		JScrollPane listPane = new JScrollPane(list);

		JPanel listContainer = new JPanel(new GridLayout(1, 1));
		listContainer.setBorder(BorderFactory.createTitledBorder("Boxes currently in the Model"));
		listContainer.add(listPane);

		add(listContainer);
	}

	private class SharedListSelectionHandler implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();

			for (int i = minIndex; i <= maxIndex; i++)
			{
				if (lsm.isSelectedIndex(i))
				{
					Part selectedPart = Part.getAllParts().get(i);

					Jme.instance.selectedGeometry = null;
					Jme.instance.select((Geometry) selectedPart.getSpatial());
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		list.setListData(Part.getAllParts().toArray(new Part[0]));
	}
}
