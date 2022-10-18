import {createContext} from "react";
import {DashboardProperty, DashboardStateProps} from "../../interface/interface";

const dashboardProps: DashboardProperty = {
    getAction(): DashboardStateProps {
        return {
            displayName: '',
            show: false
        };
    },
    setDisplayName(prop: string): void {},
    setActionProp(props: DashboardStateProps): void {},
    setActionType(action: string): void {}
}

export const DashboardContext = createContext(dashboardProps)