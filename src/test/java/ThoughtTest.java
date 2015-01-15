import autocity.core.Thought;
import autocity.core.world.buildings.Hut;
import autocity.core.civilians.prefabs.Villager;
import autocity.core.enumeration.EThoughtType;
import org.junit.Test;

public class ThoughtTest {
    @Test
    public void testThought() {
        Villager civ = new Villager();

        civ.addThought(new Thought(EThoughtType.ConstructingBuilding, new Hut()));

        for (Thought thought : civ.getThoughts()) {
            System.out.println(thought.toString());
        }
    }
}
