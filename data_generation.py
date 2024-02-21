from random import *
from csv import writer
from datetime import *
# from time import *

employees = [1,2,3,4,5]
ingredients = ["buns","lettuce","patty","cheese","bacon","bread","tomato","crispy chicken","spicy crispy chicken","grilled chicken","croutons","fries"]
menu_items = {
    "bacon cheeseburger":["buns","patty","cheese","bacon"],
    "cheeseburger":["buns","patty","cheese"],
    "patty melt":["bread","patty","cheese"],
    "hamburger":["buns","patty","lettuce","tomato"],
    "aggie chicken club":["buns","crispy chicken","lettuce","bacon"],
    "revs grilled chicken sandwich":["buns","grilled chicken","lettuce","tomato"],
    "spicy chicken sandwich":["buns","spicy crispy chicken","lettuce"],
    "chicken caesar salad":["grilled chicken","lettuce","lettuce","lettuce","croutons"],
    "french fries":["fries"]
}
menu_item_costs = {
    "bacon cheeseburger":8.65,
    "cheeseburger":7.45,
    "patty melt":5.45,
    "hamburger":8.45,
    "aggie chicken club":6.75,
    "revs grilled chicken sandwich":6.60,
    "spicy chicken sandwich":6.30,
    "chicken caesar salad":6.80,
    "french fries":3.50
}
ing_item_costs = {
    "buns": .30,
    "lettuce": .25,
    "patty": 1.50,
    "cheese": .40,
    "bacon": .50,
    "bread": .20,
    "tomato": .40,
    "crispy chicken": .75,
    "spicy crispy chicken": .85,
    "grilled chicken": .75,
    "croutons": .10,
    "fries": 1.20
}


menu_file = open("menu_items.csv","w")
menu_csv = writer(menu_file)
menu_id = 1
menu_csv.writerow(["id","name","available","price"])
menu_item_ids = {}
for x in menu_items:
    menu_csv.writerow([menu_id,x,True,format(menu_item_costs[x],".2f")])
    menu_item_ids[x] = menu_id
    menu_id += 1
menu_file.close()

inventory_count = {}
for item in menu_items:
    for ing in menu_items[item]:
        if ing in inventory_count:
            inventory_count[ing] += 1
        else:
            inventory_count[ing] = 1
for ing in inventory_count:
    inventory_count[ing] *= 400

item_file = open("inventory_items.csv","w")
item_csv = writer(item_file)
item_id = 1
item_ids = {}
item_csv.writerow(["id","item_name","stock","price"])
for x in ingredients:
    item_csv.writerow([item_id,x,inventory_count[x],format(ing_item_costs[x],".2f")])
    item_ids[x] = item_id
    item_id += 1
item_file.close()

ingredients_file = open("ingredients.csv","w")
ingr_csv = writer(ingredients_file)
ingr_id = 1
ingr_csv.writerow(["id","item_id","menu_id","num"])
for x in menu_items:
    ing_count = {}
    for ing in menu_items[x]:
        if ing in ing_count:
            ing_count[ing] += 1
        else:
            ing_count[ing] = 1
    for ing in ing_count:
        ingr_csv.writerow([ingr_id,item_ids[ing],menu_item_ids[x],ing_count[ing]])
        ingr_id += 1



available_times = []
curr_time = datetime(year=2023,month=1,day=1,hour=10,minute=30,second=0)
lunch_end = datetime(year=2023,month=1,day=1,hour=13,minute=30,second=0)
dinner_begin = datetime(year=2023,month=1,day=1,hour=18,minute=30,second=0)
dinner_end = datetime(year=2023,month=1,day=1,hour=20,minute=30,second=0)
closing_time = datetime(year=2023,month=1,day=1,hour=22,minute=30,second=0)
inc = timedelta(seconds=1)

while curr_time < closing_time:
    available_times.append(curr_time.time())
    if curr_time < lunch_end:
        curr_time = curr_time + inc
    elif curr_time < dinner_begin:
        curr_time = curr_time + inc*5
    elif curr_time < dinner_end:
        curr_time = curr_time + inc
    else:
        curr_time = curr_time + inc*3

ingredients_file.close()
day1 = date(2023,1,1)
inc = timedelta(days=1)

multipliers = [1.3,1.7,2.4,3.5,4.2,2.6,1]
num_items = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,4]
avail_emp = [1,2,2,2,3,3,3,4,4,4,5,5]

sales_file = open("sales_transactions.csv","w")
sales_csv = writer(sales_file)
sales_id = 1
sales_csv.writerow(["id","cost","employee_id","purchase_time"])

sale_item_file = open("sales_items.csv","w")
sale_item_csv = writer(sale_item_file)
sale_item_id = 1
sale_item_csv.writerow(["id","sales_id","menu_id"])

inventory_trans_file = open("inventory_transactions.csv","w")
inv_trans_csv = writer(inventory_trans_file)
inv_trans_id = 1
inv_trans_csv.writerow(["id","manager_id","transaction_date","price"])

inventory_item_file = open("inventory_item_orders.csv","w")
inv_item_csv = writer(inventory_item_file)
inv_item_id = 1
inv_item_csv.writerow(["id","transaction_id","item_id","stock","price"])

''' for reference of inventory size
inventory_count = {}
for item in menu_items:
    for ing in menu_items[item]:
        if ing in inventory_count:
            inventory_count[ing] += 1
        else:
            inventory_count[ing] = 1
for ing in inventory_count:
    inventory_count[ing] *= 400
'''

curr_inventory = inventory_count.copy()

for i in range(randrange(366,415,7)):
    day = day1 + (inc)*i
    multi = multipliers[day.weekday()]
    num_orders = randrange(int(150*multi),int(250*multi))
    times = sample(available_times,num_orders)
    for t in times:
        emp_id = choice(avail_emp)
        num = choice(num_items)
        cost = 0
        for j in range(num):
            item = choice(list(menu_items.keys()))
            for ing in menu_items[item]:
                curr_inventory[ing] -= 1
            cost += menu_item_costs[item]
            sale_item_csv.writerow([sale_item_id,sales_id,menu_item_ids[item]])
            sale_item_id += 1
        sales_csv.writerow([sales_id,format(cost,".2f"),emp_id,t.strftime("%H:%M:%S")])
        sales_id += 1
    
    if day.weekday() == 3 or day.weekday() == 6:
        restock = {}
        stock_time = time(hour=22,minute=randrange(35,60),second=randrange(0,60))
        stock_time = datetime.combine(day,stock_time)
        for item in curr_inventory:
            restock[item] = inventory_count[item] - curr_inventory[item]
        price = 0
        for item in restock:
            cost = ing_item_costs[item]*restock[item]
            price += cost
            inv_item_csv.writerow([inv_item_id,inv_trans_id,item_ids[item],restock[item],cost])
            inv_item_id += 1
        # the only manager has id = 1
        inv_trans_csv.writerow([inv_trans_id,1,stock_time.strftime("%Y-%M-%D %H:%M:%S"),format(price,".2f")])
        curr_inventory = inventory_count.copy()
        inv_trans_id += 1
        
sales_file.close()
sale_item_file.close()
inventory_trans_file.close()
inventory_item_file.close()
