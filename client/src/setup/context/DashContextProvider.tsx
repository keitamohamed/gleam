import {useState} from "react";
import {DashboardContext} from "./Context";
import {DashboardStateProps, Props} from "../../interface/interface";

const {Provider} = DashboardContext


export const DashboardContextProvider = ({children}: Props) => {
    const [action, setAction] = useState<DashboardStateProps>({
        displayName: 'dashboard',
        actionType: '',
        show: false
    })

    const getAction = () => {
      return action
    }

    const setActionProp = (props: DashboardStateProps) => {

    }
    const setDisplayName = (prop: string) => {
      setAction({
          ...action,
          displayName: prop
      })
    }
    const setActionType = (prop: string) => {
        setAction({
            ...action,
            actionType: prop,
        })
    }

    return (
        <Provider value={{
           getAction,
            setDisplayName,
            setActionProp,
           setActionType
        }}>
            {children}
        </Provider>
    )
}