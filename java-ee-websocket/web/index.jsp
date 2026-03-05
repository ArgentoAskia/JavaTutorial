<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2026/2/27
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>简单聊天室</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      max-width: 600px;
      margin: 20px auto;
      padding: 0 20px;
    }
    h1 {
      text-align: center;
      color: #333;
    }
    #status {
      text-align: center;
      padding: 5px;
      margin: 10px 0;
      border-radius: 4px;
      font-weight: bold;
    }
    .connected {
      background-color: #d4edda;
      color: #155724;
      border: 1px solid #c3e6cb;
    }
    .disconnected {
      background-color: #f8d7da;
      color: #721c24;
      border: 1px solid #f5c6cb;
    }
    #messages {
      height: 400px;
      overflow-y: scroll;
      border: 1px solid #ccc;
      padding: 10px;
      margin-bottom: 10px;
      background-color: #f9f9f9;
      border-radius: 4px;
    }
    .message {
      padding: 5px 10px;
      margin: 5px 0;
      background-color: #e1f5fe;
      border-radius: 4px;
      word-wrap: break-word;
    }
    .message.system {
      background-color: #eee;
      font-style: italic;
      color: #666;
    }
    #input-area {
      display: flex;
      gap: 10px;
    }
    #messageInput {
      flex: 1;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 14px;
    }
    #sendBtn {
      padding: 10px 20px;
      background-color: #007bff;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
    }
    #sendBtn:disabled {
      background-color: #ccc;
      cursor: not-allowed;
    }
    #sendBtn:hover:not(:disabled) {
      background-color: #0056b3;
    }
    #clearBtn {
      padding: 10px 20px;
      background-color: #6c757d;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
    }
    #clearBtn:hover {
      background-color: #5a6268;
    }
  </style>
</head>
<body>
<h1>📢 简单聊天室</h1>

<div id="status" class="disconnected">未连接</div>

<div id="messages"></div>

<div id="input-area">
  <input type="text" id="messageInput" placeholder="输入消息..." disabled>
  <button id="sendBtn" disabled>发送</button>
  <button id="clearBtn">清屏</button>
</div>

<script>
  // 获取当前应用的上下文路径（例如 /chatroom）
  const ctxPath = '${pageContext.request.contextPath}';
  // 构建 WebSocket URL（根据页面协议自动选择 ws 或 wss）
  const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
  const wsUrl = protocol + '//' + window.location.host + ctxPath + '/echo';

  let socket = null;
  let connected = false;

  // DOM 元素
  const statusDiv = document.getElementById('status');
  const messagesDiv = document.getElementById('messages');
  const input = document.getElementById('messageInput');
  const sendBtn = document.getElementById('sendBtn');
  const clearBtn = document.getElementById('clearBtn');

  // 连接 WebSocket
  function connect() {
    socket = new WebSocket(wsUrl);

    socket.onopen = function () {
      connected = true;
      statusDiv.textContent = '✅ 已连接';
      statusDiv.className = 'connected';
      input.disabled = false;
      sendBtn.disabled = false;
      addSystemMessage('已加入聊天室');
    };

    socket.onmessage = function (event) {
      // 假设服务器直接发送文本消息
      addMessage(event.data);
    };

    socket.onclose = function () {
      connected = false;
      statusDiv.textContent = '❌ 连接断开';
      statusDiv.className = 'disconnected';
      input.disabled = true;
      sendBtn.disabled = true;
      addSystemMessage('连接已断开，尝试重连...');
      // 尝试 5 秒后重连
      setTimeout(connect, 5000);
    };

    socket.onerror = function (error) {
      console.error('WebSocket 错误:', error);
      addSystemMessage('发生错误，请检查控制台');
    };
  }

  // 添加一条普通消息
  function addMessage(text) {
    const msgDiv = document.createElement('div');
    msgDiv.className = 'message';
    msgDiv.textContent = text;
    messagesDiv.appendChild(msgDiv);
    scrollToBottom();
  }

  // 添加系统消息（样式不同）
  function addSystemMessage(text) {
    const msgDiv = document.createElement('div');
    msgDiv.className = 'message system';
    msgDiv.textContent = '💬 ' + text;
    messagesDiv.appendChild(msgDiv);
    scrollToBottom();
  }

  // 滚动到底部
  function scrollToBottom() {
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
  }

  // 发送消息
  function sendMessage() {
    if (!connected) {
      alert('当前未连接，无法发送消息');
      return;
    }
    const msg = input.value.trim();
    if (msg === '') return;

    socket.send(msg);
    addMessage('我: ' + msg);  // 本地回显（可选，如果服务器也回显则不要重复）
    input.value = '';
  }

  // 清屏
  function clearMessages() {
    messagesDiv.innerHTML = '';
  }

  // 绑定事件
  sendBtn.addEventListener('click', sendMessage);
  input.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
      sendMessage();
      e.preventDefault();
    }
  });
  clearBtn.addEventListener('click', clearMessages);

  // 开始连接
  connect();
</script>
</body>
</html>
