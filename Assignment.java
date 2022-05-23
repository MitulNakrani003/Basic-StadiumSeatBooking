import java.util.Scanner;
import java.io.*;

class User
{
    String user_name;
    char block;
    char section;
    int seat_row[];
    int seat_pos[];

    User(String n, char b, char s, int no_seats)
    {
        user_name = n;
        block = b;
        section = s;
        seat_row = new int[no_seats];
        seat_pos = new int[no_seats];
    }
}

class Block
{
    char division;
    int[][] upper = new int[3][10];
    int[][] lower = new int[3][10];
}

class Stadium
{
    Block blk[] = new Block[5]; //array of blocks
    User cust[] = new User[400];
    int user_num = 0;

    Stadium()
    {
        for(int i = 0; i < 5; i++)
            blk[i] = new Block();
    }

    void price_display(int nos, int b, char s)
    {
            if((b == 0 || b == 1) && (s == 'U' || s == 'u'))
            {
                System.out.println("Your total price: " + (nos*1500));
            }
            else if((b == 0 || b == 1) && (s == 'L' || s == 'l'))
            {
                System.out.println("Your total price: " + (nos*1000));
            }
            else if((b == 2 || b == 3) && (s == 'U' || s == 'u'))
            {
                System.out.println("Your total price: " + (nos*800));
            }
            else if((b == 2 || b == 3) && (s == 'L' || s == 'l'))
            {
                System.out.println("Your total price: " + (nos*500));
            }
            else if((b == 4) && (s == 'U' || s == 'u'))
            {
                System.out.println("Your total price: " + (nos*3000));
            }
            else if((b == 4) && (s == 'L' || s == 'l'))
            {
                System.out.println("Your total price: " + (nos*2500));
            }
    }

    void seat_allotment()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        display_available();
        System.out.println("\nSeat Pricing:");
        System.out.println("Price for block A and B upper : Rs.1500");
        System.out.println("Price for block A and B lower : Rs.1000");
        System.out.println("Price for block C and D upper: Rs.800");
        System.out.println("Price for block C and D lower: Rs.500");
        System.out.println("Price for block P upper : Rs.3000");
        System.out.println("Price for block P lower : Rs.2500");
        System.out.print("\nEnter number of required seats: ");
        int req_seats = sc.nextInt();

        System.out.print("Enter your Block\n(0 for A, 1 for B, 2 for C, 3 for D, 4 for Private Block): ");
        int block_num = sc.nextInt();

        char block;
        if(block_num == 4)
             block = 'P';
        else
             block = (char)(block_num + 65);

        System.out.print("Enter block section(U for Upper or L for Lower): ");
        char sect = sc.next().charAt(0);

        cust[user_num] = new User(name, block, sect, req_seats);
        price_display(req_seats,block_num,sect);
        seat_allocation(req_seats, block_num, sect);

        user_num++;
    }

    void seat_allocation(int num_seats, int blnum, char sec)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the required seats:\n\n");
        for(int i = 0; i < num_seats; i++)
        {
            int row, seat;
            System.out.print("Enter row number(1 - 3) of the block: ");
            row = sc.nextInt();
            System.out.printf("Enter seat number (1 - 10) of row %d: ", row);
            seat = sc.nextInt();

            if(availibility_check(row - 1, seat - 1, blnum, sec))
            {
                if(sec == 'U' || sec == 'u')
                    blk[blnum].upper[row - 1][seat - 1] = 1;

                else if(sec == 'L' || sec == 'l')
                    blk[blnum].lower[row - 1][seat - 1] = 1;
                else
                {
                    System.out.println("Invalid Section.");
                    return;
                }
                System.out.println("Seat is now booked.");
            }
            else
            {
                System.out.println("Seat chosen is occupied.");
                i--;
                continue;
            }
            cust[user_num].seat_row[i] = row;
            cust[user_num].seat_pos[i] = seat;
        }
    }

    boolean availibility_check(int row, int seat, int blnum, char sec)
    {
        if(sec == 'U' || sec == 'u')
        {
            if(blk[blnum].upper[row][seat] == 0)
                return true;
            else
                return false;
        }
        else
        {
            if(blk[blnum].lower[row][seat] == 0)
                return true;
            else
                return false;
        }
    }

    void display_available()
    {
        for(int i = 0; i < 5; i++)
        {
            char blok = (char)(i + 65);
            if(i == 4)
            {
                blok = 'P';
            }

            System.out.println("Available seats in Block " + blok);
            int upper_num = 0, lower_num = 0;

            System.out.print("In Upper Section: ");
            for(int j = 0; j < 3; j++)
            {
                for(int k = 0; k < 10; k++)
                {
                    if(blk[i].upper[j][k] == 0)
                        upper_num++;
                }
            }
            System.out.println(upper_num);

            System.out.print("In Lower Section: ");
            for(int j = 0; j < 3; j++)
            {
                for(int k = 0; k < 10; k++)
                {
                    if(blk[i].lower[j][k] == 0)
                        lower_num++;
                }
            }
            System.out.println(lower_num);

            System.out.println();
        }
    }

    void display_menu()
    {
        int i;
        FileInputStream fin;

        try
        {
            fin = new FileInputStream("C:\\Users\\Mitul\\Desktop\\Assignments2020\\OOP");
            do{
                i = fin.read();
                if(i != -1) System.out.print((char) i);
            }while(i != -1);
            fin.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not Found.");
            return;
        }
        catch(IOException e)
        {
            System.out.println("IOException error.");
            return;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("ArrayIndexOutOfBoundsException error.");
            return;
        }
    }

    void seat_cancellation()
    {
        Scanner sc1 = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String del_name = sc1.nextLine();
        int flag = 0;

        for(int k = 0; k <= user_num; k++)
        {
            if((cust[k].user_name).compareTo(del_name) == 0)
            {
                flag++;
                cust[k].user_name = "";

                int del_block = (int)cust[k].block;
                if(cust[k].block == 'P')
                {
                    del_block = del_block - 76;//del_block - 80 + 4
                }
                else
                {
                    del_block = del_block - 65;
                }

                for(int j = 0; j < (cust[k].seat_row).length; j++)
                {
                    if(cust[k].section == 'U' || cust[k].section == 'u')
                    {
                        blk[del_block].upper[cust[k].seat_row[j] - 1][cust[k].seat_pos[j] - 1] = 0;
                        cust[k].seat_row[j] = 0;
                        cust[k].seat_pos[j] = 0;
                    }
                    else if (cust[k].section == 'L' || cust[k].section == 'l')
                    {
                        blk[del_block].lower[cust[k].seat_row[j] - 1][cust[k].seat_pos[j] - 1] = 0;
                        cust[k].seat_row[j] = 0;
                        cust[k].seat_pos[j] = 0;
                    }
                }

                cust[k].block = ' ';
                cust[k].section = ' ';
                System.out.println("Your seats are now cancelled.");
                break;
            }
        }
        if(flag == 0)
            System.out.println("No seats booked under this Name.");
    }

    void booking_status()
    {
        Scanner sc2 = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String book_name= sc2.nextLine();
        int flag = 0;
        for(int k = 0; k < user_num; k++)
        {
            if((cust[k].user_name).compareTo(book_name) == 0)
            {
                flag++;
                System.out.println("\nYour Booking has been confirmed.");
                System.out.println("Booking name: " + cust[k].user_name);
                System.out.println("Seat Block: " + cust[k].block);
                System.out.println("Seat Section: " + cust[k].section);
                System.out.println("U = Upper and L = Lower");
                System.out.println("Your Booked seats are Displayed below: row(seat no.)");
                for(int j = 0; j < cust[k].seat_row.length; j++)
                {
                    System.out.print(cust[k].seat_row[j] + "(" + cust[k].seat_pos[j] + ")  ");
                }
                System.out.println();
                int temp = (int)cust[k].block;
                if(cust[k].block == 'P')
                {
                    temp = temp - 76;//del_block - 80 + 4
                }
                else
                {
                    temp = temp - 65;
                }
                price_display(cust[k].seat_pos.length, temp, cust[k].section);
                break;
            }
        }
        if(flag == 0)
            System.out.println("No seats booked under this Name.");
    }
}

class Assignment
{
    public static void main(String[] args)
    {
        Stadium s = new Stadium();
        Scanner scc = new Scanner(System.in);
        System.out.println("****Stadium Seat Booking System****\n\n");
        int choice = 0;
        do{
            System.out.println("\n1.Book Seats");
            System.out.println("2.Cancel Booked Seats");
            System.out.println("3.Check Available Seats");
            System.out.println("4.Display Cafeteria Menu");
            System.out.println("5.Check your Booking");
            System.out.println("6.Exit");
            System.out.print("Enter -> ");
            choice = scc.nextInt();
            switch(choice)
            {
                case 1:
                    s.seat_allotment();
                    break;
                case 2:
                    s.seat_cancellation();
                    break;
                case 3:
                    s.display_available();
                    break;
                case 4:
                    s.display_menu();
                    break;
                case 5:
                    s.booking_status();
                    break;
            }
        }while(choice != 6);
    }
}
