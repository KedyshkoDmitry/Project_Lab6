package bsu.rfe.java.group8.lab6.Kedyshko.var7;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.Console;

public class BouncingBall implements Runnable
{
    // Максимальный радиус, который может иметь мяч
    private static final int MAX_RADIUS = 70;
    // Минимальный радиус, который может иметь мяч
    private static final int MIN_RADIUS = 10;
    // Максимальная скорость, с которой может летать мяч
    private static final int MAX_SPEED = 15;
    private Field field;
    private MouseEvent event;

    private int radius;
    private Color color;
    // Текущие координаты мяча
    private double x;
    private double y;
    // Вертикальная и горизонтальная компонента скорости
    private int speed;
    private double speedX;
    private double speedY;

    // КОНСТРУКТОР
    public BouncingBall(Field field)
    {
        // Необходимо иметь ссылку на поле, по которому прыгает мяч, чтобы отслеживать выход за его пределы через getWidth(), getHeight()
        this.field = field;
        // Радиус мяча случайного размера
        radius = new Double(Math.random()*(MAX_RADIUS - MIN_RADIUS)).intValue() + MIN_RADIUS;
        // Абсолютное значение скорости зависит от диаметра мяча, чем он больше, тем медленнее
        speed = new Double(Math.round(8*MAX_SPEED / radius)).intValue();
        if (speed > MAX_SPEED)
        {
            speed = MAX_SPEED;
        }
        // Начальное направление скорости тоже случайно, угол в пределах от 0 до 2PI
        double angle = Math.random()*2*Math.PI;
        // Вычисляются горизонтальная и вертикальная компоненты скорости
        speedX = 3*Math.cos(angle);
        speedY = 3*Math.sin(angle);
        // Цвет мяча выбирается случайно
        color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
        // Начальное положение мяча случайно
        x = Math.random()*(field.getSize().getWidth()-2*radius) + radius;
        y = Math.random()*(field.getSize().getHeight()-2*radius) + radius;
        // Создаѐм новый экземпляр потока, передавая аргументом ссылку на класс, реализующий Runnable (т.е. на себя)
        Thread thisThread = new Thread(this);
        // Запускаем поток
        thisThread.start();
    }
    // Метод run() исполняется внутри потока. Когда он завершает работу, то завершается и поток
    public void run()
    {
        try
        {
            // Крутим бесконечный цикл, т.е. пока нас не прервут, мы не намерены завершаться
            while(true)
            {
                // Синхронизация потоков на самом объекте поля Если движение разрешено - управление будет возвращено в метод В противном случае - активный поток заснѐт
                field.canMove(this);

                if (x + speedX <= radius)
                {
                    // Достигли левой стенки, отскакиваем право
                    speedX = -speedX;
                    x = radius;
                }
                else if (x + speedX >= field.getWidth() - radius)
                {
                    // Достигли правой стенки, отскок влево
                    speedX = -speedX;
                    x=new Double(field.getWidth()-radius).intValue();
                }
                else if (y + speedY <= radius)
                {
                    // Достигли верхней стенки
                    speedY = -speedY;
                    y = radius;
                }
                else if (y + speedY >= field.getHeight() - radius)
                {
                    // Достигли нижней стенки
                    speedY = -speedY;
                    y=new Double(field.getHeight()-radius).intValue();
                }
                else if (field.charismed)
                {
                    Point location = MouseInfo.getPointerInfo().getLocation();
                    double X = location.getX();
                    double Y = location.getY();
                    //double distance = Math.sqrt(Math.pow((X-x),2)+Math.pow((Y-y),2));
                    //speedX = Math.sin((X-x)/distance);
                    //speedY = Math.cos((X-x)/distance);
                    if(speedX>0)
                    {
                        if (X > x)
                        {
                            x += speedX;
                        }
                        else
                        {
                            x -= speedX;
                        }
                    }
                    else
                    {
                        if (X > x)
                        {
                            x -= speedX;
                        }
                        else
                        {
                            x += speedX;
                        }
                    }

                    if(speedY>0)
                    {
                        if (Y > y)
                        {
                            y += speedY;
                        }
                        else
                        {
                            y -= speedY;
                        }
                    }
                    else
                    {
                        if (Y > y)
                        {
                            y -= speedY;
                        }
                        else
                        {
                            y += speedY;
                        }
                    }

                }
                else
                    {
                        // Просто смещаемся
                        x += speedX;
                        y += speedY;
                    }
                // Засыпаем на X миллисекунд, где X определяется исходя из скорости Скорость = 1 (медленно), засыпаем на 15 мс. Скорость = 15 (быстро), засыпаем на 1 мс.
                Thread.sleep(16-speed);
            }
        }
        catch (InterruptedException ex)
        {
// Если нас прервали, то ничего не делаем
// и просто выходим (завершаемся)
        }
    }
    // Метод прорисовки самого себя
    public void paint(Graphics2D canvas)
    {
        canvas.setColor(color);
        canvas.setPaint(color);
        Ellipse2D.Double ball = new Ellipse2D.Double(x-radius, y-radius, 2*radius, 2*radius);
        canvas.draw(ball);
        canvas.fill(ball);
    }
}

