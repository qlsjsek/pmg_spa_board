//게시글 작성
function createBoard() {
	var selectElement = document.getElementById('categorySelect');
	var categoryId = selectElement.value;
	var boardTitle = document.getElementById('boardTitle').value;
	var boardContent = document.getElementById('boardContent').value;
	var boardImage = document.getElementById('boardImage').files[0];

	if (!boardTitle) {
		alert('제목을 입력해주세요');
		return;
	}
	if (!boardContent) {
		alert('내용을 입력해주세요');
		return;
	}

	var formData = new FormData();
	formData.append('categoryId', categoryId);
	formData.append('boardTitle', boardTitle);
	formData.append('boardContent', boardContent);
	formData.append('boardImage', boardImage);

	fetch('/api/board/create', {
		method: 'POST',
		body: formData
	})
		.then(response => {
			if (response.ok) {
				alert('게시글이 성공적으로 작성되었습니다');
				document.getElementById('boardWritePage').style.display = 'none';
				document.getElementById('boardListPage').style.display = 'block';
				location.reload();
			} else {
				console.error('Error:', response.status);
				alert('게시글 등록 실패');
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('게시글 등록 실패');
		});
}
//게시글 상세페이지
function goToBoardDetail(event) {
	const boardId = event.target.getAttribute('data-board-id');
	document.getElementById('boardDetailId').value = boardId;
	var userId = document.getElementById('detailUserId');
	var writeUserId = document.getElementById('writerUserId').value;

	if (userId !== writeUserId) {
		document.getElementById('updateBtn').style.display = "none";
		document.getElementById('deleteBtn').style.display = "none";
	}

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
			goToBoardDetail1();
			increaseReadCount(boardId);
			const titleElement = boardData.boardTitle;
			const contentElement = boardData.boardContent;
			const recommendElement = boardData.boardRecommend;
			const boardDetailTitleElement = document.getElementById('boardDetailTitle');
			const boardDetailContentElement = document.getElementById('boardDetailContent');
			const boardDetailRecommendElement = document.getElementById('boardDetailRecommend');

			boardDetailTitleElement.textContent = titleElement;
			boardDetailContentElement.textContent = contentElement;
			boardDetailRecommendElement.textContent = recommendElement;
			findBoardImage();
			document.getElementById('boardUpdateTitle').value = titleElement;
			document.getElementById('boardUpdateContent').value = contentElement;


		})
		.catch(error => {
			console.error('Error :', error);
		});
}
//boardId로 이미지 조회
function findBoardImage() {
	var boardId = document.getElementById('boardDetailId').value;
	fetch(`/api/board/image/${boardId}`)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('이미지를 가져오는데 실패했습니다');
			}
		})
		.then(imageData => {
			const imageName = imageData.imageName;
			const imageElement = document.getElementById('boardDetailImage');
			const imageUrl = '/images/' + imageName;
			imageElement.src = imageUrl;
		})
		.catch(error => {
			console.error('Error : ', error);
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
	var title = document.getElementById('boardUpdateTitle').value;
	var content = document.getElementById('boardUpdateContent').value;
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

//자유게시판 리스트
function freeBoardByCategortId() {
	const categoryId = 2;
	fetch(`/api/board/category/${categoryId}`)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('게시글을 가져오는데 실패했습니다');
			}
		})
		.then(boards => {
			const boardListContainer = document.querySelector('#boardFreeListPage ul');
			boardListContainer.innerHTML = ''
			const tableHeader = document.createElement('li');
			tableHeader.innerHTML = `
                <span class="board-number-category">번호</span>
                <span class="board-title-category">제목</span>
                <span class="board-author-category">작성자</span>
                <span class="board-good-category">추천</span>
                <span class="board-readCount-category">조회수</span>
            `;
			boardListContainer.appendChild(tableHeader);;

			boards.forEach((board, index) => {
				const listItem = document.createElement('li');
				listItem.innerHTML = `
                    <span class="board-number">${index + 1}</span>
                    <input type="hidden" id="boardListContent" value="${board.boardContent}">
                    <a href="#" class="board-title" onclick="goToBoardDetail(event)" 
                       data-board-id="${board.boardId}" 
                       data-board-title="${board.boardTitle}">
                       ${board.boardTitle}
                    </a>
                    <span class="board-author"></span>
                    <span class="board-good">${board.boardRecommend}</span>
                    <span class="board-readCount">${board.boardReadCount}</span>
                `;
				boardListContainer.appendChild(listItem);
				document.getElementById('boardListPage').style.display = 'none';
				document.getElementById('boardFreeListPage').style.display = 'block';
			});
		})
		.catch(error => {
			console.error('Error : ', error);
		});
}
//질문게시판 리스트
function questionBoardByCategortId() {
	const categoryId = 3;
	fetch(`/api/board/category/${categoryId}`)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('게시글을 가져오는데 실패했습니다');
			}
		})
		.then(boards => {
			const boardListContainer = document.querySelector('#boardQuestionListPage ul');
			boardListContainer.innerHTML = ''
			const tableHeader = document.createElement('li');
			tableHeader.innerHTML = `
                <span class="board-number-category">번호</span>
                <span class="board-title-category">제목</span>
                <span class="board-author-category">작성자</span>
                <span class="board-good-category">추천</span>
                <span class="board-readCount-category">조회수</span>
            `;
			boardListContainer.appendChild(tableHeader);;

			boards.forEach((board, index) => {
				const listItem = document.createElement('li');
				listItem.innerHTML = `
                    <span class="board-number">${index + 1}</span>
                    <input type="hidden" id="boardListContent" value="${board.boardContent}">
                    <a href="#" class="board-title" onclick="goToBoardDetail(event)" 
                       data-board-id="${board.boardId}" 
                       data-board-title="${board.boardTitle}">
                       ${board.boardTitle}
                    </a>
                    <span class="board-author"></span>
                    <span class="board-good">${board.boardRecommend}</span>
                    <span class="board-readCount">${board.boardReadCount}</span>
                `;
				boardListContainer.appendChild(listItem);
				document.getElementById('boardListPage').style.display = 'none';
				document.getElementById('boardQuestionListPage').style.display = 'block';
			});
		})
		.catch(error => {
			console.error('Error : ', error);
		});
}
//기타게시판 리스트
function restBoardByCategortId() {
	const categoryId = 4;
	fetch(`/api/board/category/${categoryId}`)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('게시글을 가져오는데 실패했습니다');
			}
		})
		.then(boards => {
			const boardListContainer = document.querySelector('#boardRestListPage ul');
			boardListContainer.innerHTML = ''
			const tableHeader = document.createElement('li');
			tableHeader.innerHTML = `
                <span class="board-number-category">번호</span>
                <span class="board-title-category">제목</span>
                <span class="board-author-category">작성자</span>
                <span class="board-good-category">추천</span>
                <span class="board-readCount-category">조회수</span>
            `;
			boardListContainer.appendChild(tableHeader);;

			boards.forEach((board, index) => {
				const listItem = document.createElement('li');
				listItem.innerHTML = `
                    <span class="board-number">${index + 1}</span>
                    <input type="hidden" id="boardListContent" value="${board.boardContent}">
                    <a href="#" class="board-title" onclick="goToBoardDetail(event)" 
                       data-board-id="${board.boardId}" 
                       data-board-title="${board.boardTitle}">
                       ${board.boardTitle}
                    </a>
                    <span class="board-author"></span>
                    <span class="board-good">${board.boardRecommend}</span>
                    <span class="board-readCount">${board.boardReadCount}</span>
                `;
				boardListContainer.appendChild(listItem);
				document.getElementById('boardListPage').style.display = 'none';
				document.getElementById('boardRestListPage').style.display = 'block';
			});
		})
		.catch(error => {
			console.error('Error : ', error);
		});
}

//검색
function searchBoard() {
	const searchInput = document.getElementById('searchInput').value.trim();

	if (!searchInput) {
		return;
	}

	fetch(`/api/board/searchByTitle?keyword=${searchInput}`)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('게시글 검색에 실패했습니다.');
			}
		})
		.then(boards => {
			const boardListContainer = document.querySelector('#boardListPage ul');
			boardListContainer.innerHTML = ''
			const tableHeader = document.createElement('li');
			tableHeader.innerHTML = `
                <span class="board-number-category">번호</span>
                <span class="board-title-category">제목</span>
                <span class="board-author-category">작성자</span>
                <span class="board-good-category">추천</span>
                <span class="board-readCount-category">조회수</span>
            `;
			boardListContainer.appendChild(tableHeader);;

			boards.forEach((board, index) => {
				const listItem = document.createElement('li');
				listItem.innerHTML = `
                    <span class="board-number">${index + 1}</span>
                    <input type="hidden" id="boardListContent" value="${board.boardContent}">
                    <a href="#" class="board-title" onclick="goToBoardDetail(event)" 
                       data-board-id="${board.boardId}" 
                       data-board-title="${board.boardTitle}">
                       ${board.boardTitle}
                    </a>
                    <span class="board-author"></span>
                    <span class="board-good">${board.boardRecommend}</span>
                    <span class="board-readCount">${board.boardReadCount}</span>
                `;
				boardListContainer.appendChild(listItem);
			});
		})
		.catch(error => {
			console.error('Error: ', error);
			alert('게시글 검색에 실패했습니다.');
		});
}

//조회수 증가
function increaseReadCount(boardId) {
	fetch(`/api/board/readCount/${boardId}`, {
		method: 'GET'
	})
		.then(response => {
			if (response.ok) {
				console.log('조회수 증가 성공')
			} else {
				console.log('조회수 증가 실패 : ', response.statusText);
			}
		})
		.catch(error => {
			console.error('Error : ', error);
		});

}


//게시글 추천
function recommend() {
	var boardId = document.getElementById('boardDetailId').value;
	var recommendCountElement = document.getElementById('boardDetailRecommend');
	var recommendCount = parseInt(recommendCountElement.innerText) + 1;
	fetch(`/api/board/recommend/${boardId}?boardRecommend=${recommendCount}`, {
		method: 'PUT',
	})
		.then(response => {
			if (response.ok) {
				return response.text();
			} else {
				throw new Error('추천 실패');
			}
		})
		.then(message => {
			console.log(message);
			const recommendCountElement = document.getElementById('boardDetailRecommend');
			var recommendCount = parseInt(recommendCountElement.innerText);
			recommendCount++;
			recommendCountElement.innerText = recommendCount;

		})
		.catch(error => {
			console.error('Error :', error);
		});
}


//전체 게시글 정렬















function goToPage(pageId) {
	const pages = ['loginPage', 'forgotUserIdForm', 'forgotPasswordForm', 'boardListPage', 'registerPage', 'profilePage',
		'editProfilePage', 'boardDetailPage', 'boardEditPage', 'boardWritePage', 'boardQuestionListPage', 'boardFreeListPage', 'boardRestListPage'];


	pages.forEach(page => {
		const element = document.getElementById(page);
		if (element) {
			element.style.display = 'none';
			clearText();
		}
	});

	const pageElement = document.getElementById(pageId);
	if (pageElement) {
		pageElement.style.display = 'block';
	}
}
function clearText() {
	document.getElementById("userId").value = "";
	document.getElementById("userPassword").value = "";
	document.getElementById("confirmPassword").value = "";
	document.getElementById("userName").value = "";
	document.getElementById("userBirthDate").value = "";
	document.getElementById("userPhone").value = "";
	document.getElementById("userAddress").value = "";
	document.getElementById("userEmail").value = "";
	document.getElementById("loginUserId").value = "";
	document.getElementById("loginUserPassword").value = "";
}


function goToBoardQuestionList() {
	goToPage('boardQuestionListPage');
	questionBoardByCategortId();
}
function goToBoardFreeList() {
	goToPage('boardFreeListPage');
	freeBoardByCategortId();
}
function goToBoardRestList() {
	goToPage('boardRestListPage');
	restBoardByCategortId();
}
function goToBoardDetail1() {
	goToPage('boardDetailPage');
}