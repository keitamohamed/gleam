import {HiSpeakerphone} from "react-icons/hi";
import {AiFillFileAdd, AiFillFolderAdd} from "react-icons/ai";
import {BsChatSquareText} from "react-icons/bs";

export const Card = () => {

    return (
        <div className="card">
            <div className="header">
            </div>
            <div className="body">
                <div className="text_container">
                    <h2>History 101</h2>
                    <h5>HIST-101</h5>
                    <p>Spring 2019</p>
                </div>
                <div className="icons">
                    <HiSpeakerphone/>
                    <AiFillFileAdd/>
                    <BsChatSquareText/>
                    <AiFillFolderAdd/>
                </div>
            </div>
        </div>
    )
}