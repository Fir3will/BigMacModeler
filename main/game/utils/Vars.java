package main.game.utils;

import java.util.Random;

public class Vars
{
	public static boolean isDebug;

	public static String getRandomTip()
	{
		int rand = new Random().nextInt(16);

		switch (rand)
		{
			case 0:
				return "The Model is going to, almost always, seem MUCH smaller in game!";
			case 1:
				return "Adding a new box will always be in the middle and a 1x1x1 Cube!";
			case 2:
				return "The Rotation isn't by Degrees, But actually by Radian! So 2 PI is a full rotation!";
			case 3:
				return "FINALLY! A MODELER FOR MAC AND LINUX!";
			case 4:
				return "Look at that Spider GO!";
			case 5:
				return "Cloning a box doesn't clone the exact name!";
			case 6:
				return "The '+' in the middle of the screen is how you select Boxes!";
			case 7:
				return "I'm always open to suggestions! Leave a comment on my MCF Topic!";
			case 8:
				return "Got a Crash? Get the file in the Logs folder and send that to me with a quick Before and After!";
			case 9:
				return "A 1x1x1 Box is just One Texture Pixel in Game!";
			case 10:
				return "Use the Plank below the surface for a Guide on how it'll look!";
			case 11:
				return "Let me know any questions you have reguarding function and features!";
			case 12:
				return "Sorry about no Texture Map, I will try my best to get that in future updates!";
			case 13:
				return "YOU'VE BEEN WAITING FOREVER! I KNOW! I CAN'T BELIEVE IT EITHER!";
			case 14:
				return "Gotta LOVE the Name!";
		}

		return "You BETTER be enjoying yourself with this!";
	}
}
