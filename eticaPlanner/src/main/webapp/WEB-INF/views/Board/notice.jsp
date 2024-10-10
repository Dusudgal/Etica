<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, maximum-scale=1.0, minimum-scale=1.0">
    <title>공지사항 게시판</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        body {
            background-color: #ffffff; /* 하얀색 배경으로 변경 */
            font-family: 'Noto Sans KR', sans-serif;
            margin: 0;
            padding: 20px;
        }

        .wrap {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #d63384;
        }

        input, textarea {
            width: 100%;
            border-radius: 5px;
            border: 1px solid #d63384;
            padding: 10px;
            box-sizing: border-box;
            margin-bottom: 10px;
            font-size: 16px;
        }

        textarea {
            height: 100px;
            resize: none;
        }

        button {
            width: 100%;
            background-color: #d63384;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #c02a6d;
        }

        .card {
            background-color: #f8d3e0;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .username {
            font-weight: bold;
            color: #d63384;
        }

        .date {
            font-size: 12px;
            color: gray;
        }

        .delete-btn, .update-btn {
            margin-top: 10px;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .delete-btn {
            background-color: #ff4d4f;
            color: white;
        }

        .delete-btn:hover {
            background-color: #c41c2e;
        }

        .update-btn {
            background-color: #ffa500;
            color: white;
        }

        .update-btn:hover {
            background-color: #e59400;
        }

        .edit-area {
            display: none;
        }
    </style>
</head>
<body>
<div class="wrap">
    <h2>공지사항 게시판</h2>
    <input type="text" id="title" placeholder="제목을 입력하세요">
    <textarea id="contents" placeholder="공지사항을 입력하세요"></textarea>
    <button onclick="postMessage()">공지 올리기</button>
    <div id="message-list"></div>
</div>

<script>
    function escapeHtml(unsafe) {
        return unsafe
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }

    function formatDate(dateString) {
        var date = new Date(dateString);
        return date.getFullYear() + '-' +
            String(date.getMonth() + 1).padStart(2, '0') + '-' +
            String(date.getDate()).padStart(2, '0') + ' ' +
            String(date.getHours()).padStart(2, '0') + ':' +
            String(date.getMinutes()).padStart(2, '0');
    }

    function createMessageHtml(msg) {
        return '<div class="card" id="msg-' + msg.id + '">' +
            '<p class="username">' + escapeHtml(msg.username) + '</p>' +
            '<p class="date">작성일: ' + formatDate(msg.createdAt) + '</p>' +
            '<p class="title">제목: ' + escapeHtml(msg.title) + '</p>' +  // 제목
            '<p class="contents">' + escapeHtml(msg.contents) + '</p>' +
            '<button class="delete-btn" onclick="deleteMessage(' + msg.id + ')">삭제</button>' +
            '<button class="update-btn" onclick="editMessage(' + msg.id + ')">수정</button>' +
            '<div class="edit-area" id="edit-area-' + msg.id + '">' +
            '<input id="edit-title-' + msg.id + '" value="' + escapeHtml(msg.title) + '">' +
            '<textarea id="edit-contents-' + msg.id + '">' + escapeHtml(msg.contents) + '</textarea>' +
            '<button onclick="updateMessage(' + msg.id + ')">수정 완료</button>' +
            '</div>' +
            '</div>';
    }

    function postMessage() {
        var title = $('#title').val();
        var contents = $('#contents').val();

        if (title.trim() === '' || contents.trim() === '') {
            alert('제목과 내용을 모두 입력해주세요');
            return;
        }

        var message = {
            username: 'admin', // 관리자 이름으로 고정
            title: title,
            contents: contents
        };

        $.ajax({
            type: 'POST',
            url: '/api/memos',
            contentType: 'application/json',
            data: JSON.stringify(message),
            success: function(response) {
                alert('공지사항이 등록되었습니다.');
                $('#title').val('');
                $('#contents').val('');
                getMessages();
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('공지사항 등록에 실패했습니다.');
            }
        });
    }

    function getMessages() {
        $.ajax({
            type: 'GET',
            url: '/api/memos',
            success: function(response) {
                var messageList = $('#message-list');
                messageList.empty();
                response.forEach(function(msg) {
                    messageList.append(createMessageHtml(msg));
                });
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('공지사항을 불러오는데 실패했습니다.');
            }
        });
    }

    function editMessage(id) {
        $('#edit-area-' + id).toggle();
    }

    function updateMessage(id) {
        var newTitle = $('#edit-title-' + id).val();
        var newContents = $('#edit-contents-' + id).val();

        if (newTitle.trim() === '' || newContents.trim() === '') {
            alert('제목과 내용을 모두 입력해주세요');
            return;
        }

        var message = {
            title: newTitle,
            contents: newContents
        };

        $.ajax({
            type: 'PUT',
            url: '/api/memos/' + id,
            contentType: 'application/json',
            data: JSON.stringify(message),
            success: function(response) {
                alert('공지사항이 수정되었습니다.');
                getMessages();
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('공지사항 수정에 실패했습니다.');
            }
        });
    }

    function deleteMessage(id) {
        if (confirm('정말로 이 공지사항을 삭제하시겠습니까?')) {
            $.ajax({
                type: 'DELETE',
                url: '/api/memos/' + id,
                success: function(response) {
                    alert('공지사항이 삭제되었습니다.');
                    getMessages();
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                    alert('공지사항 삭제에 실패했습니다.');
                }
            });
        }
    }

    $(document).ready(function() {
        getMessages();

        $(window).resize(function() {
            window.resizeTo(600, 600);
        });

        document.addEventListener('touchmove', function(event) {
            if (event.scale !== 1) {
                event.preventDefault();
            }
        }, { passive: false });
    });
</script>
</body>
</html>
