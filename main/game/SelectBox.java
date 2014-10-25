package main.game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.game.utils.JavaHelp;
import com.jme3.scene.Spatial;

public class SelectBox extends JPanel implements ActionListener
{
	private final Timer timer;
	public ListSelectionModel listSelectionModel;
	public JList<String> list;
	ArrayList<String> strings = new ArrayList<String>();

	public SelectBox()
	{
		super(new BorderLayout());
		setFocusable(true);
		strings = JavaHelp.newArrayList();
		list = new JList<String>(new String[0]);
		listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
		JScrollPane listPane = new JScrollPane(list);

		JPanel listContainer = new JPanel(new GridLayout(1, 1));
		listContainer.setBorder(BorderFactory.createTitledBorder("Boxes currently in the Model"));
		listContainer.add(listPane);

		add(listContainer);

		timer = new Timer(400, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.gc();
		if (Jme.instance.allParts == null) return;
		strings.clear();

		for (int i = 0; i < Jme.instance.allParts.getQuantity(); i++)
		{
			strings.add(Jme.instance.allParts.getChild(i).getName());
		}

		list.setListData(strings.toArray(new String[0]));
	}

	private class SharedListSelectionHandler implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			System.gc();
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();

			for (int i = minIndex; i <= maxIndex; i++)
			{
				if (lsm.isSelectedIndex(i))
				{
					for (int j = 0; j < Jme.instance.allParts.getQuantity(); j++)
					{
						if (strings.get(j).equals(Jme.instance.allParts.getChild(j).getName()))
						{
							Jme.instance.unSelect(Jme.instance.selectedPart);
							Jme.instance.select((Part) Jme.instance.allParts.getChildren().toArray(new Spatial[0])[i]);
						}
					}
				}
			}
		}
	}

	private static final long serialVersionUID = 1L;
}
