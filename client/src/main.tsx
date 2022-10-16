import React from 'react'
import ReactDOM from 'react-dom/client'
import {Provider} from "react-redux"
import store from "./setup/academy/init"
import App from './App'
import {DashboardContextProvider} from "./setup/context/DashContextProvider";

import "./index.scss"


const root = ReactDOM.createRoot(document.getElementById("root")!)

root.render(
    <Provider store={store}>
        <DashboardContextProvider>
            <App/>
        </DashboardContextProvider>
    </Provider>
)
