package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

public class NumKL implements KeyListener
{
	private final JTextField selectedField;

	public NumKL(JTextField selectedField)
	{
		this.selectedField = selectedField;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		try
		{
			pressed();
		}
		catch (NumberFormatException f)
		{
			selectedField.setText("0");
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		try
		{
			pressed();
		}
		catch (NumberFormatException f)
		{
			selectedField.setText("0");
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		try
		{
			pressed();
		}
		catch (NumberFormatException f)
		{
			selectedField.setText("0");
		}
	}

	private void pressed() throws NumberFormatException
	{
		String text = selectedField.getText();

		for (int i = 0; i < text.length(); i++)
		{
			if (selectedField == AddBoxPanel.boxName)
			{
				if (!Character.isJavaIdentifierStart(text.charAt(i)) && !Character.isJavaIdentifierPart(text.charAt(i)))
				{
					text = text.replace(text.charAt(i) + "", "");
					selectedField.setText(text);
				}
			}
			else if (selectedField == AddBoxPanel.sizeX || selectedField == AddBoxPanel.sizeY || selectedField == AddBoxPanel.sizeZ || selectedField == AddBoxPanel.texOffsetX || selectedField == AddBoxPanel.texOffsetY || selectedField == AddBoxPanel.rotX || selectedField == AddBoxPanel.rotY || selectedField == AddBoxPanel.rotZ)
			{
				if (text.isEmpty()) text = "0";

				if (!Character.isDigit(text.charAt(i)) && text.charAt(i) != '.')
				{
					text = text.replace(text.charAt(i) + "", "");
					selectedField.setText(text);
				}
			}
			else
			{
				if (text.isEmpty()) text = "0";

				if (!Character.isDigit(text.charAt(i)) && text.charAt(i) != '.' && text.charAt(i) != '-')
				{
					text = text.replace(text.charAt(i) + "", "");
					selectedField.setText(text);
				}
			}
		}
		Jme.instance.setSelectedBoxVars(AddBoxPanel.boxName.getText(), Float.parseFloat(AddBoxPanel.boxX.getText()), Float.parseFloat(AddBoxPanel.boxY.getText()), Float.parseFloat(AddBoxPanel.boxZ.getText()), Float.parseFloat(AddBoxPanel.sizeX.getText()), Float.parseFloat(AddBoxPanel.sizeY.getText()), Float.parseFloat(AddBoxPanel.sizeZ.getText()), AddBoxPanel.mirror, Integer.parseInt(AddBoxPanel.texOffsetX.getText()), Integer.parseInt(AddBoxPanel.texOffsetY.getText()), Float.parseFloat(AddBoxPanel.rotX.getText()), Float.parseFloat(AddBoxPanel.rotY.getText()), Float.parseFloat(AddBoxPanel.rotZ.getText()));
	}
}
