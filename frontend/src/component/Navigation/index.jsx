import {NavLink} from "react-router-dom";
import './index.css';
import React from "react";

export const Navigation = () => {

    return (
        <aside className="sidebar">
            <h1>Medical Assurance</h1>
            <nav>
                <ul>
                    <li>
                        <NavLink
                            className="link-class"
                            to='/'
                            exact
                            activeClassName="active-link"
                        >
                            Assurance
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            className="link-class"
                            to='/refund'
                            activeClassName="active-link"
                        >
                            Refunds
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            className="link-class"
                            to='/operations'
                            activeClassName="active-link"
                        >
                            Available Operations
                        </NavLink>
                    </li>
                </ul>
            </nav>
        </aside>

    )
}
