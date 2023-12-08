Day 07  
--Part One--  
You are invited to play a game of Camel Cards, which is similar in structure to poker. You get a list of hands and your goal
is to order them based on strength. A hand consists of five cards labeled one of A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2, where
A is the highest strength card and 2 is the lowest. Every hand has one type, from strongest to weakest they are:  
  
Five of a kind, where all five cards have the same label: AAAAA
Four of a kind, where four cards have the same label and one card has a different label: AA8AA  
Full house, where three cards have the same label, and the remaining two cards share a different label: 23332  
Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card 
in the hand: TTT98  
Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a 
third label: 23432  
One pair, where two cards share one label, and the other three cards have a different label from the pair and 
each other: A23A4  
High card, where all cards' labels are distinct: 23456  
  
You are given a list of hands and their corresponding bid (Day07Text.txt). You must evaluate each hand and rank them from weakest
hand to strongest hand (for a five hand game: 1 - 5). Any hand's winnings is then the product of that hand's rank multiplied by
that hand's bid. Your total winnings would then be the sum of all these winnings.
For your entire puzzle input, what is your total winnings?
