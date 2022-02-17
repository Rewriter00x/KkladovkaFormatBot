import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);

        add(new JPanel(){
            {
                setSize(MyFrame.this.getWidth(),MyFrame.this.getHeight());
                setLayout(null);
                add(new JLabel("Bot is running"){
                    {
                        setFont(new Font("Arial",Font.PLAIN,25));
                        setBounds(MyFrame.this.getWidth()/4,MyFrame.this.getHeight()/3,MyFrame.this.getWidth()/2,25);
                        setHorizontalAlignment(SwingConstants.CENTER);
                        setOpaque(true);
                    }
                });

                add(new JButton("Power off") {
                    {
                        setFont(new Font("Arial",Font.PLAIN,25));
                        setBounds(MyFrame.this.getWidth()/4,MyFrame.this.getHeight()*2/3,MyFrame.this.getWidth()/2,MyFrame.this.getHeight()/9);
                        setOpaque(true);
                        addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                            }
                        });
                    }
                });
            }
        });

        revalidate();
        repaint();
        setVisible(true);
    }
}
