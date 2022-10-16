import {BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import {DashboardContextProvider} from "../context/DashContextProvider";

import {routePath} from "./RoutePath";

const RouteSwitcher = () => {

    return (
        <BrowserRouter>

            <Routes>
                {
                    routePath.map((route, index) => {
                        const isAuthenticate = true;
                        return (
                            route.protected ? (
                                    <Route
                                        key={`${route.name}_${index}`}
                                        {...route}
                                        element={isAuthenticate ?
                                                <route.component/>:
                                            <Navigate replace to={"/"}/>}
                                    />
                            ) : <Route key={`${route.name}_${index}`} path={route.path} element={<route.component/>}/>
                        )
                    })
                }
            </Routes>
        </BrowserRouter>
    )
}

export default RouteSwitcher