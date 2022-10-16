import {SideNave} from "../reusable/SideNave";

export const Student = () => {

    return (
        <div className={"stud_layout grid grid-cols-12 gap-2"}>
            <SideNave/>
            <div className="main col-span-8">
                <h1>Main container</h1>
            </div>
            <div className="right-side-nav w-full">
                <h1>Right container</h1>
            </div>
        </div>
    )
  
}