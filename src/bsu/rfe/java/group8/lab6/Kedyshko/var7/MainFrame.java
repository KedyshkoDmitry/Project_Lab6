package bsu.rfe.java.group8.lab6.Kedyshko.var7;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
    // РАЗМЕР ОКНА
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    private JMenuItem charismaMenuItem;
    private JMenuItem notCharismaMenuItem;
    // ПОЛЕ С МЯЧАМИ
    private Field field = new Field();
    // КОНСТРУКТОР ГЛАВНОГО ОКНА
    public MainFrame()
    {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        // ОТЦЕНТРОВКА ОКНА
        setLocation((kit.getScreenSize().width - WIDTH)/2,(kit.getScreenSize().height - HEIGHT)/2);
        // РАЗВЕРНУТЬ ОКНО
        setExtendedState(MAXIMIZED_BOTH);
        // СОЗДАНИЕ МЕНЮ
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Мячи");
        Action addBallAction = new AbstractAction("Добавить мяч")
        {
            public void actionPerformed(ActionEvent event)
            {
                field.addBall();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled())
                {
                    // АКТИВАЦИЯ ПАУЗЫ
                    pauseMenuItem.setEnabled(true);
                    charismaMenuItem.setEnabled(true);
                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);
        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Приостановить движение")
        {
            public void actionPerformed(ActionEvent event)
            {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
                charismaMenuItem.setEnabled(true);
                notCharismaMenuItem.setEnabled(false);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);

        Action resumeAction = new AbstractAction("Возобновить движение")
        {
            public void actionPerformed(ActionEvent event)
            {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
                charismaMenuItem.setEnabled(true);
                notCharismaMenuItem.setEnabled(false);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);

        Action charismaAction = new AbstractAction("Харизма")
        {
            public void actionPerformed(ActionEvent event)
            {
                field.charisma();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(false);
                charismaMenuItem.setEnabled(false);
                notCharismaMenuItem.setEnabled(true);
            }
        };
        charismaMenuItem = controlMenu.add(charismaAction);
        charismaMenuItem.setEnabled(false);

        Action notCharismaAction = new AbstractAction("Отключить харизма")
        {
            public void actionPerformed(ActionEvent event)
            {
                field.notCharisma();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
                charismaMenuItem.setEnabled(true);
                notCharismaMenuItem.setEnabled(false);
            }
        };
        notCharismaMenuItem = controlMenu.add(notCharismaAction);
        notCharismaMenuItem.setEnabled(false);

        // Добавить в центр граничной компоновки поле Field
        getContentPane().add(field, BorderLayout.CENTER);
    }
    // ГЛАВНЫЙ МЕТОД
    public static void main(String[] args)
    {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
