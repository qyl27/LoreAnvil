# LoreAnvil
A bukkit plugin for changing name or lore of an item stack with anvil.  
一个用铁砧来修改物品名称和介绍的Bukkit插件。

## Versions（版本）
Native Minecraft Version: **1.18.2**  
Tested Minecraft Version: **1.19.4**, **1.18.2**, **1.16.4**  
Supported Minecraft Version: **1.19.4-1.13**

## Usage（使用方法）

1. Modify item name: Place the item to modify to the left slot in anvil, then input the name.
2. Append item lore: Place the item to modify to the left slot, paper to the middle slot in anvil, then input the lore.
3. Remove item lore: Place the item to modify to the middle slot in anvil.


1. 修改名称：把要修改的物品放在铁砧左边的格子，中间什么都不放，输入名称。
2. 添加介绍：把要修改的物品放在铁砧左边的格子，中间放纸，输入介绍。
3. 删除介绍：把要修改的物品放在铁砧中间的格子，左边什么都不放。

**Supports style code starts with `&`.**  
**支持以 `&` 开头的样式代码，即16种颜色。**  
**Supports Hex color code like `&#66ccff` if running above 1.16.**
**如果运行在1.16及以上版本，还支持R！G！B！（使用形如 `&#66ccff` 的样式代码）**

## Permission（权限）
- loreanvil.name —— Modify item name.（修改物品名称。）
- loreanvil.lore —— Append item lore.（添加物品介绍。）
- loreanvil.color —— Use style code in name or lore of item.（在物品的名称及介绍中使用颜色代码。）
- loreanvil.remove —— Remove item lore.（删除物品介绍。）

**All player have these permissions by default.**  
**权限默认全部玩家拥有。**

## Bug report （Bug反馈/催更）
Please go to the issues page.  
请到 Issues 页面提出。

## Gallery（效果展示）

![修改名称](https://github.com/qyl27/LoreAnvil/raw/main/img/1.png)
![添加介绍](https://github.com/qyl27/LoreAnvil/raw/main/img/2.png)
![删除介绍](https://github.com/qyl27/LoreAnvil/raw/main/img/3.png)
![删除介绍](https://github.com/qyl27/LoreAnvil/raw/main/img/4.png)

## Todo
- [ ] 修复特定状态下的体验问题；
- [ ] 删除特定物品自定义名称和介绍；
- [ ] 禁止修改特定物品的名称和介绍。
