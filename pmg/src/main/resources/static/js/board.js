//게시글 작성
function createBoard() {
	var selectElement = document.getElementById('categorySelect');
	var categoryId = selectElement.value;
	var boardTitle = document.getElementById('boardTitle').value;
	var boardContent = document.getElementById('boardContent').value;
	if (!boardTitle) {
		alert('제목을 입력해주세요');
		return;
	}
	if (!boardContent) {
		alert('내용을 입력해주세요');
		return;
	}

	var boardDto = {
		categoryId: categoryId,
		boardTitle: boardTitle,
		boardContent: boardContent
	}

	fetch('/api/board/create', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(boardDto)
	})
		.then(response => {
			if (response.ok) {
				alert('게시글이 성공적으로 작성되었습니다');
				document.getElementById('boardWritePage').style.display = 'none';
				document.getElementById('boardListPage').style.display = 'block';

			} else {
				console.error('Error : ' + error);
				alert('게시글 등록 실패');
			}
		})
		.catch(error => {
			console.error('Error : ' + error);
			alert('게시글 등록 실패');
		});
}
//게시글 상세페이지
function goToBoardDetail(event) {
	const boardId = event.target.getAttribute('data-board-id');
	const boardTitle = event.target.textContent;
	const contentElement = event.target.nextElementSibling.textContent;
	document.getElementById('boardDetailId').value = boardId;
	event.preventDefault();
	fetch('/api/board/detail/' + boardId)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('게시글을 가져오는데 실패했습니다.');
			}
		})
		.then(boardData => {
			const titleElement = boardData.boardTitle;
			const contentElement = boardData.boardContent;
			const boardDetailTitleElement = document.getElementById('boardDetailTitle');
			const boardDetailContentElement = document.getElementById('boardDetailContent');
			boardDetailTitleElement.textContent = titleElement;
			boardDetailContentElement.textContent = contentElement;

			document.getElementById('boardListPage').style.display = 'none';
			document.getElementById('boardDetailPage').style.display = 'block';

		})
		.catch(error => {
			console.error('Error :', error);
		});
}

//게시글 삭제
function deleteBoard() {
	var boardId = document.getElementById('boardDetailId').value;
	console.log(boardId);

	if (!confirm('정말 이 게시글을 삭제하시겠습니까?')) {
		return;
	}

	fetch(`/api/board/delete/${boardId}`, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			if (response.ok) {
				alert('게시글이 성공적으로 삭제되었습니다.');
				document.getElementById('boardDetailPage').style.display = 'none';
				document.getElementById('boardListPage').style.display = 'block';
				location.reload();
			} else {
				throw new Error('게시글 삭제에 실패했습니다.');
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('게시글 삭제 중 오류가 발생했습니다.');
		});
}

//게시글 수정
function updateBoard() {
	var boardId = document.getElementById('boardDetailId').value;
	var title = document.getElementById('boardDetailTitle').textContent;
	var boardContentElement = document.getElementById('boardDetailContent');
	var content = boardContentElement.textContent.trim();
	document.getElementById('boardUpdateTitle').value = title;
	document.getElementById('boardUpdateContent').textContent = content;
	console.log(document.getElementById('boardUpdateTitle').value);
	console.log(content);
	if (!confirm('게시글을 수정하시겠습니까?')) {
		return;
	}
	if (!title) {
		alert('제목을 입력해주세요');
		return;
	}
	if (!content) {
		alert('내용을 입력해주세요');
		return;
	}

	fetch(`/api/board/update/${boardId}`, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			boardTitle: title,
			boardContent: content
		})
	})
		.then(response => {
			if (response.ok) {
				alert('게시글 수정 성공');
				document.getElementById('boardEditPage').style.display = 'none';
				document.getElementById('boardListPage').style.display = 'block';
				location.reload();
			} else {
				throw new Error('게시글 수정 실패');
			}
		})
		.catch(error => {
			console.error('Error : ', error);
			alert('게시글 수정 실패');
		});

}

