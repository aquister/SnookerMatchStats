import javax.swing.*;

public class MatchRun
{
    public static void main(String[] args)
    {

        JFrame frame = new JFrame ("Snooker Match");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MatchPanel(Integer.parseInt(JOptionPane.showInputDialog(null, "Number of red balls...", "Reds", JOptionPane.QUESTION_MESSAGE))));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

}
