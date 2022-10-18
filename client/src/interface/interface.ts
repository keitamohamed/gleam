import { ReactNode } from "react";

export interface Props {
    children?: ReactNode
}

export interface DashboardStateProps {
    displayName?: string;
    actionType?: string;
    show: boolean
}

export interface DashboardProperty {
    getAction: () => DashboardStateProps,
    setDisplayName: (prop: string) => void,
    setActionProp: (props: DashboardStateProps) => void,
    setActionType: (action: string) => void
}