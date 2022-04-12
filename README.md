# LoreAnvil
A bukkit plugin for changing lore of item stack with anvil.

一个用铁砧来修改物品名称和介绍的Bukkit插件。

## 版本
开发时的版本：**1.18.2**  
测试过的版本：**1.18.2**、**1.16.4**  
理论支持版本：**1.18-1.13**

## 使用方法：

1. 修改名称：把要修改的物品放在铁砧左边的格子，中间什么都不放，输入名称。
2. 添加介绍：把要修改的物品放在铁砧左边的格子，中间放纸，输入介绍。
3. 删除介绍：把要修改的物品放在铁砧中间的格子，左边什么都不放。

**支持以 `&` 开头的样式代码，即16种颜色。**  
**如果运行在1.16及以上版本，还支持R！G！B！（使用形如 `#66ccff` 的样式代码）**

## 权限节点：
- loreanvil.name —— 修改物品名称。
- loreanvil.lore —— 添加物品介绍。
- loreanvil.color —— 在物品的名称及介绍中使用颜色代码。
- loreanvil.remove —— 删除物品介绍。

**以上权限默认全部玩家拥有。**

## Bug反馈：

QQ群：616440544

## 效果展示：

![修改名称](https://github.com/qyl27/LoreAnvil/raw/main/img/1.png)
![添加介绍](https://github.com/qyl27/LoreAnvil/raw/main/img/2.png)
![删除介绍](https://github.com/qyl27/LoreAnvil/raw/main/img/3.png)
![删除介绍](https://github.com/qyl27/LoreAnvil/raw/main/img/4.png)

## Todo：
1. 修复体验问题；
2. 删除特定物品自定义名称和介绍；
3. 禁止修改特定物品的名称和介绍。