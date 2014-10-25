package main.game.saving;

public interface Savable
{
	public void loadFromTag(DataTag tag);

	public void saveToTag(DataTag tag);
}
