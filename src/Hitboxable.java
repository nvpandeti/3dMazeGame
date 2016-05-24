/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.util.*;
public interface Hitboxable
{
	/**
	 * Returns hitboxes
	 * @return hitboxes
	 */
	ArrayList<Hitbox> getHitbox();
	/**
	 * Returns a list of the hitboxable's Faces
	 * @return a list of the hitboxable's Faces
	 */
	ArrayList<Face> getFaces();
}