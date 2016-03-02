Q1: For which communication(s) do you use the SSL encryption? If you are encrypting the communication from (1) to (2) in Figure 2, for example, write (1)→(2) in your answer.

We used SSL encryption for steps (4)->(5) (sending the credit card input) and (5)->(6) (servlet response). This was encrypted to prevent a customer's credit card number from being intercepted.

Q2: How do you ensure that the item was purchased exactly at the Buy_Price of that particular item?
Note: If you get help from any source other than those mentioned in this page, at the end of your README, please clearly cite all references you use, and breifly explain how they help you, such as which portion(s) is/are particularly helpful.

We ensured the item was purchased exactly at the Buy_Price by storing the item received from the Oak server in a session. Every servlet then retrieves the item itself and of its information, including Buy_Price, directly from the session. The user never has a chance to modify the buy price of the item. 