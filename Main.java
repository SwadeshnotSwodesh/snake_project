 import java.util.Scanner;
import java.util.Random;

class SnakeGame 
{
    Scanner scan = new Scanner(System.in);
    boolean gameOver;

    enum eDirection {
        STOP, 
        LEFT,
        RIGHT, 
        UP,
        DOWN
    }

    eDirection dir;
    int width = 40;
    int height = 20;
    int x;
    int y;
    int fruitX;
    int fruitY;
    int score;
    int[] tailX = new int[100];
    int[] tailY = new int[100];
    int nTail = 0;

    public SnakeGame() 
    {
        gameOver = false;
        dir = eDirection.STOP;
        x = width / 2;
        y = height / 2;
        fruitX = Math.abs(new Random().nextInt() % width);
        fruitY = Math.abs(new Random().nextInt() % height);
        score = 0;
        nTail = 0;
    }

    public void Input() 
    {
        //if (System.in.available()>0) {
            //char ch = (char) System.in.read();
            char ch=scan.next().charAt(0);
            switch (ch) {
                case 'a':
                    dir = eDirection.LEFT;
                    break;
                case 'd':
                    dir = eDirection.RIGHT;
                    break;
                case 'w':
                    dir = eDirection.UP;
                    break;
                case 's':
                    dir = eDirection.DOWN;
                    break;
                case 'x':
                    gameOver = true;
                    break;
            }
        //}
    }

    public void Logic() 
    {
        // ... (previous code)
        //formation of tail 
        int prevX = tailX[0];
        int prevY = tailY[0];
        tailX[0] = x;
        tailY[0] = y;
        int prev2X, prev2Y;
        for (int i = 1; i < nTail; i++)
        {
            prev2X = tailX[i];
            prev2Y = tailY[i];
            tailX[i] = prevX;
            tailY[i] = prevY;
            prevX = prev2X;
            prevY = prev2Y;
        }

        //movement of snake
        switch (dir)
        {
        case LEFT:
            x--;
            break;
        case RIGHT:
            x++;
            break;
        case UP:
            y--;
            break;
        case DOWN:
            y++;
            break;
        default:
            break;
        }

        //logic if snake touches its own body or wall
        if (x > width || x < 0 || y > height || y < 0)
        {
            gameOver = true;
            System.out.println( "Game Over!");
            System.out.println( "Press 'c' to play again or 'q' to quit");
            char choice;
            do
            {
                choice = scan.next().charAt(0);//instead of _getch();
            } while (choice != 'c' && choice != 'q');
            if (choice == 'c')
            {
                menu(); // restart the game
            }
            if (choice == 'q')
            {
                System.exit(0);
            }
        }

        //logic for if snake touches its own body
        for (int i = 0; i < nTail; i++)
        {
            if (tailX[i] == x && tailY[i] == y)
            {
                gameOver = true;
                System.out.println( "Game Over!");
                System.out.println( "Press 'c' to play again or 'q' to quit");
                char choice;
                do
                {
                    choice =scan.next().charAt(0);//instead of _getch();
                } while (choice != 'c' && choice != 'q');
                if (choice == 'c')
                {
                    menu(); // restart the game
                }
                if (choice == 'q')
                {
                    System.exit(0);
                }
            }
        }

        //increment of score 
       // fruitX = Math.abs(new Random().nextInt() % width);
    //fruitY = Math.abs(new Random().nextInt() % height);
        if (x == fruitX && y == fruitY)
        {
            score += 10;
            fruitX = Math.abs(new Random().nextInt() % width);
            fruitY = Math.abs(new Random().nextInt() % height);
            nTail++;
        }
    }

    public void Run() 
    {
        Thread inputThread = new Thread(() -> {
            while (!gameOver) {
                Input();
            }
        });
        inputThread.start();

        while (!gameOver) 
        {
            Draw();
            Logic();
            try {
                Thread.sleep(200);
            }
             catch (InterruptedException e)
              {
                e.printStackTrace();
            }
        }

        // Wait for the input thread to finish
        try 
        {
            inputThread.join();
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }

    public void Draw()
     {
        // ... (previous code)
        
    //system("cls");
        //top horizontal line
        for (int i = 0; i < width + 2; i++)
        {
            System.out.format("=");
           // System.out.println("");
        }

        //vertical lines
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (j == 0)
                    System.out.format("||");

                if (i == y && j == x)
                    System.out.format("O"); //snake

                else if (i == fruitY && j == fruitX)
                    System.out.format("\3");   //fruit

                else
                {
                    boolean print = false;
                    for (int k = 0; k < nTail; k++)
                    {
                        if (tailX[k] == j && tailY[k] == i)
                        {
                            System.out.format( "O");
                            print = true;
                        }
                    }
                    if (!print)
                        System.out.format( " ");
                }

                if (j == width - 1)
                    System.out.format( "||");
            }
            System.out.println("");
        }

        // bottom horizontal line
        for (int i = 0; i < width + 2; i++)
            System.out.format( "=");
          System.out.println("");
        System.out.println( "Press 'w' to move forward, 's' to move backward, 'a' to move left and 'd' to move right!");
        System.out.println( "RULES: If you hit a wall or your own body, game over!");
        System.out.println("");
        System.out.println( "Score :"+score);
    }

    public void menu()
     {
        int choice;

        do {
            System.out.println("\n\n\n");
            System.out.println("=======================================");
            System.out.println("|                                     |");
            System.out.println("|        Snake Game Main Menu         |");
            System.out.println("|                                     |");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("|   1) Start game         |");
            System.out.println("|                         |");
            System.out.println("|   2) Exit               |");
            System.out.println("                           ");
            System.out.print("Please select! : ");
            choice = scan.nextInt();

            switch (choice) 
            {
                case 1:
                    Run();
                    break;
                case 2:
                    System.exit(0);
                default:
                    System.out.println("Please select from above options!");
            }
        } while (choice != 1);
    }
}

public class Main 
{
    public static void main(String[] args) 
    {
        SnakeGame sg = new SnakeGame();
        sg.menu();
    }
}






 