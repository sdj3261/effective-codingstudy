import React from 'react';
import { Link } from 'react-router-dom';

function Sidebar() {
    return (
        <aside className="App-sidebar">
            <nav>
                <ul>
                    <li><Link to="/problems">문제 풀이</Link></li>
                    <li><Link to="/resources">학습 자료</Link></li>
                    <li><Link to="/progress">진행 상황</Link></li>
                    <li><Link to="/settings">설정</Link></li>
                </ul>
            </nav>
        </aside>
    );
}

export default Sidebar;