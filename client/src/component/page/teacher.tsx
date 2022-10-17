import {SideNave} from "../reusable/SideNave";
import {useContext} from "react";
import {DashboardContext} from "../../setup/context/Context";

import {Card} from "../reusable/course_card";
import {Teacher_Profile} from "../sup_page/teacher_profile";
import {useTeacher} from "../../hook/useTeacher";

export const Teacher = () => {
    const dash = useContext(DashboardContext)
    const {} = useTeacher()

    return (
        <div className={"teacher_layout grid grid-cols-9 gap-2"}>
            <SideNave/>
            <div className="main grid grid-cols-3 col-span-6">
                {
                    dash.getAction().displayName == 'account' ?
                        <Teacher_Profile />
                        :
                        dash.getAction().displayName == 'dashboard' ?
                            <Card/>
                        : <h1>H1 value</h1>
                }
            </div>
            <div className="right-side-nav w-full">
                <h1>Right container</h1>
            </div>
        </div>
    )
}