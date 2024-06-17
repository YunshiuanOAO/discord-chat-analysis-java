# discord-chat-analysis-java

## 組員
F74124723周昀軒 F74124749林子宸

## 專案規劃目標
目標：創造一個可以分析discord用戶訊息流量的discord bot
想法:將訊息流量視覺化，並基於其他不同面向的分析

### 功能簡介
使用工具與套件：JDA,git,Gradle,Vue,github,hackmd,discord

### 爬蟲
[使用者輸入](https://github.com/l-zch/discord-chat-analysis-java/blob/main/app/src/main/java/app/SlashBot.java)：透過使用者在 discord 聊天室輸入，取得需要進行的操作
[爬蟲](https://github.com/l-zch/discord-chat-analysis-java/blob/main/app/src/main/java/crawler/Crawler.java)：取得伺服器聊天室的所有訊息 json
### 資料可視化
[可視化](https://github.com/l-zch/discord-stat-frontend)：使用 Vue 將所有訊息經過篩選後輸出使用者需要的資料
[伺服器](https://github.com/l-zch/discord-chat-analysis-java/blob/main/app/src/main/java/app/CustomHttpServer.java)：使用 jwebserver 將產生的檔案放進伺服器中並將網址回傳給使用者


### 操作流程

首先先開啟discord bot ，接下來判斷使用者輸入指令，如果輸入/clawer dicord bot 就會執行爬蟲程式
如果輸入/server discord bot 就會執行server 程式開啟伺服器並且回傳網址

![image](https://hackmd.io/_uploads/rJ10MBarC.png)




## 組員分工
周昀軒:slash server,clawer
林子宸:clawer 資料可視化

## 協作
* 在[hackmd](https://hackmd.io/@yunshiuan/ByNZ37dSC)上整理專案的想法或功能
* 使用git進行專案版本控制

## 遇到的問題
* **git 不熟悉** 


## demo
測試伺服器：某高中資訊研究社discord伺服器
