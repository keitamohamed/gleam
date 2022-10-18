import {PatternFormat} from "react-number-format";
import {AiOutlineEdit, AiOutlineClose} from 'react-icons/ai'
import {useAppDispatch, useAppStore} from "../../setup/academy/useReduxHook";
import {accordion} from "../../util/accordion";

import image from "/src/assets/image/photo.jpg"
import {useContext, useEffect} from "react";
import {teacherAction} from "../../setup/academy/teacher_slice";
import {DashboardContext} from "../../setup/context/Context";

export const Teacher_Profile = () => {
    const dashCtx = useContext(DashboardContext)
    const dispatch = useAppDispatch()
    const {teacher, addresses, auth} = useAppStore((state) => state.teacher)

    const setSelectedAddress = (id: number, action: string) => {
        const location = addresses.find(a => a.addressID == id);
        dispatch(teacherAction.setSelectedAddress(location))
        dashCtx.setActionType(action);
    }

    useEffect(() => {
        accordion()
    }, [])

    return (
        <div className={`profile w-full`}>
            <div className="layout grid grid-cols-12 gap-x-16">
                <div className="life_side col-start-1 col-end-4">
                    <div className="image_container">
                        <img
                            className="w-24 h-24 md:w-48 md:h-auto md:rounded-md rounded-full mx-aut"
                            src={image} alt="img"/>
                    </div>
                    <div className="information">
                        <span className={'add_'}>Information</span>
                        <section>
                            <p className="info"><span>Name: </span>{teacher.name}</p>
                            <p><span>Gender: </span>{teacher.gender} </p>
                            <p><span>Phone: </span>{teacher.phone}</p>
                        </section>
                    </div>
                    <div className="information">
                        <span className={'add_'}>Addresses</span>
                        {
                            addresses.map((a, index) => {
                                return (
                                    <section className='address' key={`${a.state}_${index}`}>
                                        <p>
                                            {a.street}<br/>{a.city}, {a.state}
                                        </p>
                                    </section>
                                )
                            })
                        }
                    </div>
                    <div className="information">
                        <span className={'add_'}>Skills</span>
                        <section>
                            <p className="info"><span>Name: </span>{teacher.name}</p>
                            <p><span>Gender: </span>{teacher.gender} </p>
                            <p><span>Phone: </span>{teacher.phone}</p>
                        </section>
                    </div>
                </div>
                <div className="main grid grid-cols-1 grid-cols-1 col-span-8">
                    <div className="section_top">
                        <div className="info_container">
                            <h3>{teacher.name}</h3>
                            <p>Web Developer</p>
                            <p><span>Email: </span>{auth.email}</p>
                        </div>
                    </div>
                    <div className="accordion w-full">
                        <section className="accordion-tabs">
                            <li className="accordion-tab" data-actab-id={"1"}>Tab 1</li>
                            <li className="accordion-tab" data-actab-id={"2"}>Edit</li>
                            <li className="accordion-tab" data-actab-id = {"3"}>Tab 3</li>
                            <li className="accordion-tab" data-actab-id = {"4"}>Edit Info</li>
                        </section>
                        <div className="accordion-content">
                            <section className="tab-section activate_section" data-actab-id={"1"}>
                                Tab 1
                            </section>
                            <section className="tab-section" data-actab-id={"2"}>
                                tab 2
                            </section>
                            <section className="tab-section" data-actab-id={"3"}>
                                Tab 3
                            </section>
                            <section className="tab-section" data-actab-id={"4"}>
                               <div className="container">
                                   {
                                       addresses.map((a, index) => {
                                           return (
                                               <section className="address !w-4/6" key={`${index}_${a.state}`}>
                                                   <div className="edit_container">
                                                       <li onClick={() => setSelectedAddress(a.addressID, 'editAddress')}>
                                                           <AiOutlineEdit/>
                                                       </li>
                                                       <li onClick={() => setSelectedAddress(a.addressID, 'deleteAddress')}>
                                                           <AiOutlineClose/>
                                                       </li>
                                                   </div>
                                                   <div className="address_container">
                                                       <section
                                                           className=''
                                                           key={`${a.state}_${index}`}
                                                       >
                                                           <li><span>Street: </span><p>{a.street}</p></li>
                                                           <li><span>City: </span><p>{a.city}</p></li>
                                                           <li><span>State: </span><p>{a.state}</p></li>

                                                       </section>
                                                   </div>
                                               </section>
                                           )
                                       })
                                   }
                               </div>
                            </section>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}