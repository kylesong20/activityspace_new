import defaultSettings from '@/settings'

const title = defaultSettings.title || '高校活动场地管理系统'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
