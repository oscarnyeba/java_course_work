# SK Motors Auction System

Vehicle auction app that tracks bids and calculates profit/loss.

## What It Does

- Register a vehicle (registration, cost, balance)
- Collect bids from 3 bidders
- Find the highest bidder
- Calculate profit or loss on the sale

## How to Run

```bash
# Compile
javac ./src/sk/motors/auction/system/SKMotorsAuctionSystem.java

# Run
java sk.motors.auction.system.SKMotorsAuctionSystem
```

Or just open in your Java IDE and run.

## Example Flow

```
Enter vehicle registration number: UAH 123X
Enter vehicle cost: 25000000
Enter current balance: 5000000

Enter bidder 1 name: John
Enter bid amount: 28000000

Enter bidder 2 name: Mary
Enter bid amount: 30000000

Enter bidder 3 name: Peter
Enter bid amount: 29500000

RESULT:
Winning Bidder: Mary
Winning Bid: 30,000,000
PROFIT: 10,000,000 ✓
```

## How Profit is Calculated

- **Total Deposits** = Winning Bid + Vehicle Balance
- **Profit/Loss** = Total Deposits - Vehicle Cost

## Authors

- **Ogwang Gift Gideon** - VU-BCS-2503-0706-EVE
- **Suubi Deborah** - VU-DIT-2503-1213-EVE
- **Nyeba Oscar Mathew** - VU-BCS-2503-1204-EVE
- **Nalwoga Madrine** - VU-BIT-2503-2460-EVE
- **Namagambe Precious** - VU-BSC-2503-0355-EVE
- **KINTU BRIAN** - VU-DIT-2503-0306-EVE


**Course**: Object Oriented Programming  
**Institution**: Victoria University