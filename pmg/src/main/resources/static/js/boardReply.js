//댓글 작성
function createReply() {
	var userIdElement = document.getElementById('detailUserId');
	var boardIdElement = document.getElementById('boardDetailId');
	var boardReplyContentElement = document.getElementById('boardReplyContent');

	if (!boardReplyContentElement || !boardReplyContentElement.value) {
		alert('내용을 입력해주세요');
		return;
	}

	if (!userIdElement || !userIdElement.value) {
		alert('회원만 댓글을 작성할 수 있습니다');
		return;
	}

	var userId = userIdElement.value;
	var boardId = boardIdElement.value;
	var boardReplyContent = boardReplyContentElement.value;

	var data = {
		userId: userId,
		boardId: boardId,
		boardReplyContent: boardReplyContent
	};

	fetch('/api/boardReply/create', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	})
		.then(response => {
			if (response.ok) {
				alert('댓글이 등록되었습니다');
				replyList();
				boardReplyContentElement.value = '';
			} else {
				console.error(response.status);
				alert('댓글 등록 실패');
			}
		})
		.catch(error => {
			console.error(error);
			alert('댓글 등록 실패');
		});
}

//댓글 리스트
async function replyList() {
	var loginUserElement = document.getElementById('detailUserId');
	if (loginUserElement) {
		var loginUser = loginUserElement.value;
	}
	try {
		var boardId = document.getElementById('boardDetailId').value;
		const response = await fetch('/api/boardReply/replyList/' + boardId);

		if (!response.ok) {
			throw new Error('댓글을 불러오는데 실패했습니다');
		}

		const replies = await response.json();

		replies.sort((a, b) => {
			return new Date(a.createdTime) - new Date(b.createdTime);
		});

		var commentList = document.getElementById('commentList');
		commentList.innerHTML = '';

		for (let i = 0; i < replies.length; i++) {
			const reply = replies[i];
			try {
				const userId = await findUserIdByBoardReplyId(reply.boardReplyId);

				var commentDiv = document.createElement('div');
				commentDiv.classList.add('col-md-12');
				var createdTime = formatDate(reply.createdTime);

				var commentContent = `
                    <div class="comment">
                        <strong>${userId}</strong>
                        <p>${reply.boardReplyContent}</p>
                        <span class="timestamp">${createdTime}</span>
                        <button class="btn btn-primary" onclick="deleteReply('${reply.boardReplyId}', ${i})">삭제하기</button>
                    </div>
                    <hr>
                `;

				commentDiv.innerHTML = commentContent;
				commentList.appendChild(commentDiv);

				if (userId !== loginUser) {
					commentDiv.querySelector(`button[onclick="deleteReply('${reply.boardReplyId}', ${i})"]`).style.display = 'none';
				}
			} catch (error) {
				console.error('댓글 작성자 정보를 가져오는데 실패했습니다', error);
			}
		}
	} catch (error) {
		console.error('댓글을 불러오는데 실패했습니다', error);
	}
}

//댓글 작성 날짜
function formatDate(dateTimeString) {
	var date = new Date(dateTimeString);
	var year = date.getFullYear();
	var month = ('0' + (date.getMonth() + 1)).slice(-2);
	var day = ('0' + date.getDate()).slice(-2);
	var hours = ('0' + date.getHours()).slice(-2);
	var minutes = ('0' + date.getMinutes()).slice(-2);

	return `${year}/${month}/${day} ${hours}:${minutes}`;
}

//댓글 userId
function findUserIdByBoardReplyId(boardReplyId) {
	return fetch(`/api/boardReply/userId/${boardReplyId}`)
		.then(response => {
			if (response.ok) {
				return response.text();
			} else {
				throw new Error('댓글 작성자 정보를 가져오는데 실패했습니다');
			}
		})
		.catch(error => {
			console.error(error);
		});
}

//댓글 삭제
function deleteReply(boardReplyId) {
	fetch(`/api/boardReply/delete/${boardReplyId}`, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			if (response.ok) {
				alert('댓글이 삭제되었습니다');
				replyList();
			} else {
				throw new Error('댓글 삭제 실패');
			}
		})
		.catch(error => {
			console.error(error);
		});
}

