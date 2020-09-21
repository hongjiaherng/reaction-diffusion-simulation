import javax.swing.*;
import java.awt.*;

class Simulation {

    Simulation() {
        JFrame jframe = new JFrame("Reaction-diffusion Simulator");
        ReactionDiffusion jpanel = new ReactionDiffusion();

        jframe.setSize(800, 620);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);

//        GridBagLayout gridBagLayout = new GridBagLayout();
//        GridBagConstraints gridBagConstraints = new GridBagConstraints();
//        jframe.setLayout(gridBagLayout);

//        JLabel advSettingLabel = new JLabel("Advanced settings");
//
//        JLabel feedRateLabel = new JLabel("Feed rate");
//
//        JLabel killRateLabel = new JLabel("Kill rate");
//
//        TextField feedRateTextField = new TextField(Double.toString(jpanel.feedRate));
//
//        TextField killRateTextField = new TextField(Double.toString(jpanel.killRate));
//
//        ImageIcon incr = new ImageIcon("res/increment.png");
//        ImageIcon decr = new ImageIcon("res/decrement.png");
//        ImageIcon play = new ImageIcon("res/play");
//        ImageIcon pause = new ImageIcon("res/pause");
//
//        JButton incrBtn = new JButton(incr);
//        JButton decrBtn = new JButton(decr);
//        JButton pauseBtn = new JButton(pause);
//        JButton clearBtn = new JButton("Clear");
//
//        // Define the grid bag.
//        gridBagConstraints.weightx = 0;
//        gridBagConstraints.weighty = 0;
//        gridBagConstraints.anchor = GridBagConstraints.EAST;
//        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
//        gridBagLayout.setConstraints(jpanel, gridBagConstraints);

        jframe.add(jpanel);

        jframe.setVisible(true);
        jframe.add(jpanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                new Simulation();
            }
        );
    }
}