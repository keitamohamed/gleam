import { ReactNode } from "react";

export interface Props {
    children?: ReactNode
}

export interface DashboardStateProps {
    displayName?: string;
    show: boolean
}

export interface DashboardProperty {
    getAction: () => DashboardStateProps,
    setActionProp: (props: DashboardStateProps) => void,
    setActionType: (action: string) => void
}