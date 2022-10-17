import {PatternFormat} from "react-number-format";
import {useAppStore} from "../../setup/academy/useReduxHook";

import image from "/src/assets/image/photo.jpg"

export const Teacher_Profile = () => {
    const {teacher, address} = useAppStore((state) => state.teacher)
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
                            address.map((a) => {
                                return (
                                    <section className='address'>
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
                        </div>
                    </div>
                    <div className="accordion w-full">
                        <section className="accordion-tabs">
                            <li className="accordion-tab">Tab 1</li>
                            <li className="accordion-tab">Edit</li>
                            <li className="accordion-tab">Tab 3</li>
                            <li className="accordion-tab">Tab 4</li>
                        </section>
                        <div className="accordion-content">
                            <section className="section"></section>
                            <section className="section"></section>
                            <section className="section"></section>
                            <section className="section"></section>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}