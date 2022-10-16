import {RiDashboardFill} from "react-icons/ri";
import {GoBook, GoCalendar} from "react-icons/go";
import {BsPeopleFill} from "react-icons/bs";
import {FiInbox} from "react-icons/fi";

import photo from "/src/assets/image/photo.jpg"
import {useContext} from "react";
import {DashboardContext} from "../../setup/context/Context";

export const SideNave = () => {
    const dash = useContext(DashboardContext)

    return (
        <div className="side-nav col-start-1 col-end-2 pt-2">
            <div className="list_container">
                <li className={"round_image"}
                    onClick={
                        () => dash.setActionType("account")}
                >
                    <img
                        className={"md:w-48 rounded-full mx-auto"}
                        src={photo}
                        alt="image"
                    />
                    <span>Account</span>
                </li>
                <li onClick={
                    () => dash.setActionType("dashboard")}
                >
                    <RiDashboardFill/>
                    <span>Dashboard</span>
                </li>
                <li onClick={
                    () => dash.setActionType("courses")}
                >
                    <GoBook/>
                    <span>Courses</span>
                </li>
                <li onClick={
                    () => dash.setActionType("group")}
                >
                    <BsPeopleFill/>
                    <span>Groups</span>
                </li>
                <li onClick={
                    () => dash.setActionType("calendar")}
                >
                    <GoCalendar/>
                    <span>Calendar</span>
                </li>
                <li onClick={
                    () => dash.setActionType("inbox")}
                >
                    <FiInbox/>
                    <span>Inbox</span>
                </li>
            </div>
        </div>
    )
}