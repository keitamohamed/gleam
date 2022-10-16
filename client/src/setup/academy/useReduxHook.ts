import {TypedUseSelectorHook, useDispatch, useSelector} from 'react-redux'
import type {AppDispatch, AppStore} from './init'

export const useAppDispatch = () => useDispatch<AppDispatch>()
export const useAppStore: TypedUseSelectorHook<AppStore> = useSelector