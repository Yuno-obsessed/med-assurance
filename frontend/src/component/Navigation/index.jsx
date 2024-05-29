import {Link} from "react-router-dom";
import React from "react";

export const Navigation = () => {
    return (
        <aside className="sidebar">
            <h1>Medical Assurance</h1>
            <nav>
                <ul>
                    <li><Link to='/'>Assurance</Link></li>
                    <li><Link to="/refund">Refunds</Link></li>
                    <li><Link to="/operations">Available Operations</Link></li>
                </ul>
            </nav>
        </aside>

    )
}
